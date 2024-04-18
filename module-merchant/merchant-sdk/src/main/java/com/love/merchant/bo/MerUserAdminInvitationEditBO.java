package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminInvitationEditBO implements Serializable {
    private Long id;
    private String email;
    private Integer status;
}
