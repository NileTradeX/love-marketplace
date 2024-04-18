package com.love.common.client;

import com.love.common.bo.UploadFile;
import com.love.common.dto.UploadFileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "common-service-api", contextId = "fileUploadFeignClient")
public interface FileUploadFeignClient {

    @PostMapping("upload")
    UploadFileDTO upload(UploadFile uploadFile);
}
