package com.love.common.controller;

import com.love.common.bo.UploadFile;
import com.love.common.dto.UploadFileDTO;
import com.love.common.result.Result;
import com.love.common.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final AwsS3Service awsS3Service;

    @PostMapping("upload")
    public Result<UploadFileDTO> upload(@RequestBody UploadFile uploadFile) {
        return Result.success(awsS3Service.uploadFile(uploadFile));
    }
}
