package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class MerUserAdminInvitationSaveBO implements Serializable {
    private String bizName;
    private String email;
    private BigDecimal commissionFeeRate;
    private String mpa;
}
