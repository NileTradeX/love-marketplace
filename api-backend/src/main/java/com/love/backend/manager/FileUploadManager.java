package com.love.backend.manager;


import com.love.common.bo.UploadFile;
import com.love.common.client.FileUploadFeignClient;
import com.love.common.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileUploadManager {

    private final FileUploadFeignClient fileUploadFeignClient;

    public UploadFileDTO upload(MultipartFile file) throws IOException {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriName(file.getOriginalFilename());
        uploadFile.setSize(file.getSize());
        uploadFile.setData(file.getBytes());
        uploadFile.setContentType(file.getContentType());
        return fileUploadFeignClient.upload(uploadFile);
    }
}
