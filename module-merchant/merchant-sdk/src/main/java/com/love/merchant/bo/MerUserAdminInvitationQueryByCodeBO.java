package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminInvitationQueryByCodeBO implements Serializable {
    private String code;
}
