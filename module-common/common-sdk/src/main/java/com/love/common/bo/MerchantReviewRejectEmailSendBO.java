package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantReviewRejectEmailSendBO implements Serializable {
    private String toEmail;
    private String bizName;
    private String reason;
    private String registerUrl;
}
