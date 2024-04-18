package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MerUserAdminBusinessInfoEditBO implements Serializable {
    private Long adminId;
    private String bizName;
    private Integer bizType;
    private Integer ownership;
    private Date incorDate;
    private String website;
    private String bizPhoneNumber;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String address;
    private String bizOrderMgmtEmail;
    private String defaultCarrier;
    private Integer bizSize;
}
