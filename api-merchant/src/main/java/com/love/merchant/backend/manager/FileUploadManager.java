package com.love.merchant.backend.manager;


import com.love.common.bo.UploadFile;
import com.love.common.client.FileUploadFeignClient;
import com.love.common.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileUploadManager {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadManager.class);

    private final FileUploadFeignClient fileUploadFeignClient;

    public UploadFileDTO upload(MultipartFile file, long uid) throws IOException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriName(file.getOriginalFilename());
        uploadFile.setSize(file.getSize());
        uploadFile.setData(file.getBytes());
        uploadFile.setContentType(file.getContentType());
        uploadFile.setUserId(uid);

        logger.warn("upload file: name = {} , size={} , contentType={} , uid={}", uploadFile.getOriName(), uploadFile.getSize(), uploadFile.getContentType(), uid);

        return fileUploadFeignClient.upload(uploadFile);
    }
}
