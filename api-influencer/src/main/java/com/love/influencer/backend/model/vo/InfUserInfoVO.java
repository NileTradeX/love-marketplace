package com.love.influencer.backend.model.vo;

import com.love.influencer.dto.InfUserAddressDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InfUserInfoVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private String avatar;
    private String displayName;
    private String firstName;
    private String lastName;
    private String description;
    private String generalIntroduction;
    private String socialLinks;
    private String paypalAccount;
    private BigDecimal commissionRate;
    private String code;
    private Long storeId;
    private InfUserAddressDTO userAddress;
}
