package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantInvitationEmailSendBO implements Serializable {
    private String bizName;
    private String inviteUrl;
    private String toEmail;
}
