package com.love.common.service;

import com.love.common.bo.UploadFile;
import com.love.common.dto.UploadFileDTO;

public interface AwsS3Service {
    UploadFileDTO uploadFile(UploadFile uploadFile);
}
