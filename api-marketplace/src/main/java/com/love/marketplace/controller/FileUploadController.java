package com.love.marketplace.controller;

import com.love.common.dto.UploadFileDTO;
import com.love.common.result.Result;
import com.love.marketplace.manager.FileUploadManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "FileUpload", description = "All File upload operation")
public class FileUploadController {

    private final FileUploadManager fileUploadManager;


    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(operationId = "uploadFile")
    public Result<UploadFileDTO> upload(@RequestPart("file") MultipartFile file) throws IOException {
        return Result.success(fileUploadManager.upload(file));
    }
}
