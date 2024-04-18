package com.love.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.love.common.bo.UploadFile;
import com.love.common.dto.UploadFileDTO;
import com.love.common.entity.Resource;
import com.love.common.enums.FileType;
import com.love.common.service.AwsS3Service;
import com.love.common.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class AwsS3ServiceImpl implements AwsS3Service, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(AwsS3ServiceImpl.class);

    private final TaskExecutor taskExecutor;

    private static final String BUCKET_NAME = "love.files";

    private S3Client s3Client;

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;

    private final Environment environment;

    private final ResourceService resourceService;

    private String dir;

    public AwsS3ServiceImpl(Environment environment, TaskExecutor taskExecutor, ResourceService resourceService) {
        this.environment = environment;
        this.taskExecutor = taskExecutor;
        this.resourceService = resourceService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.s3Client = S3Client.builder().region(Region.US_WEST_1).credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey)).build();
        this.dir = this.environment.getActiveProfiles()[0];
    }

    private String ext(String oriName) {
        if (oriName.contains(".")) {
            int idx = oriName.lastIndexOf(".");
            return oriName.substring(idx + 1).toLowerCase();
        }
        return ".xyz";
    }

    private static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").substring(8, 24);
    }

    private synchronized String key(String ext) {
        FileType type = FileType.type(ext);
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return this.dir + "/" + dateStr + "/" + type.name().toLowerCase() + "/" + uuid() + "." + ext;
    }

    public UploadFileDTO uploadFile(UploadFile uploadFile) {
        UploadFileDTO uploadFileDTO = BeanUtil.copyProperties(uploadFile, UploadFileDTO.class);
        try {
            String ext = ext(uploadFile.getOriName());
            String key = key(ext);
            s3Client.putObject(PutObjectRequest
                            .builder()
                            .bucket(BUCKET_NAME)
                            .key(key)
                            .contentLength(uploadFile.getSize())
                            .contentType(uploadFile.getContentType())
                            .build()
                    , RequestBody.fromBytes(uploadFile.getData()));
            uploadFileDTO.setKey(key);
            uploadFileDTO.setExt(ext);

            Long uid = uploadFile.getUserId();
            if (Objects.nonNull(uid) && uid > 0) {
                taskExecutor.execute(() -> {
                    Resource resource = new Resource();
                    resource.setUserId(uploadFile.getUserId());
                    resource.setKey(key);
                    resource.setExt(ext);
                    resource.setSize(uploadFile.getSize());
                    resource.setOriName(uploadFile.getOriName());
                    resource.setCreateTime(LocalDateTime.now());
                    resource.setUpdateTime(LocalDateTime.now());

                    try {
                        FileType type = FileType.type(ext);
                        resource.setType(type.getType());
                        if (type.equals(FileType.IMAGE)) {
                            try {
                                BufferedImage image = ImageIO.read(new ByteArrayInputStream(uploadFile.getData()));
                                resource.setWidth(image.getWidth());
                                resource.setHeight(image.getHeight());
                            } catch (Exception ignored) {
                                resource.setWidth(0);
                                resource.setHeight(0);
                            }
                        }
                        resourceService.save(resource);
                    } catch (Exception ex) {
                        logger.error("save resource error , key= {}", key, ex);
                    }
                });
            }
            return uploadFileDTO;
        } catch (Exception e) {
            logger.error("upload file to s3 error", e);
        }
        return null;
    }
}
