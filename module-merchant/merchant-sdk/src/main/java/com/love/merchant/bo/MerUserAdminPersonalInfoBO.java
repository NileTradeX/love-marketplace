package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MerUserAdminPersonalInfoBO implements Serializable {
    private Long adminId;
    private String firstName;
    private String lastName;
    private String title;
    private Date birthday;
    private String phoneNumber;
}
