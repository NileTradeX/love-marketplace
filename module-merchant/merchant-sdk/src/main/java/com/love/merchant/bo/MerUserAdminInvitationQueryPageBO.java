package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminInvitationQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String bizName;
    private String email;
    private Integer status;
}
