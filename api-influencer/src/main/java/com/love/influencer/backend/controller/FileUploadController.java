package com.love.influencer.backend.controller;

import com.love.common.Constants;
import com.love.common.dto.UploadFileDTO;
import com.love.common.result.Result;
import com.love.influencer.backend.manager.FileUploadManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("influencer/file")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "FileUploadApi", description = "All File upload operation")
public class FileUploadController {

    private final FileUploadManager fileUploadManager;

    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(operationId = "uploadFile")
    public Result<UploadFileDTO> upload(@RequestPart("file") MultipartFile file, @RequestHeader(Constants.USER_ID) Long uid) throws IOException {
        return Result.success(fileUploadManager.upload(file, uid));
    }

}
