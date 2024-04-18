package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class MerUserAdminFullVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private String reason;
    private String mpa;
    private BigDecimal commissionFeeRate;
    private LocalDateTime createTime;
    private MerUserAdminPersonalInfoVO personalInfo;
    private MerUserAdminBusinessInfoVO businessInfo;
    private List<BrandVO> brands;

    @Data
    public static class MerUserAdminPersonalInfoVO implements Serializable {
        private Long adminId;
        private String firstName;
        private String lastName;
        private String title;
        private Date birthday;
        private String phoneNumber;
    }

    @Data
    public static class MerUserAdminBusinessInfoVO implements Serializable {
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

}
