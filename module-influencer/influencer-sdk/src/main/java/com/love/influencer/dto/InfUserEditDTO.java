package com.love.influencer.dto;

import com.love.influencer.bo.InfUserAddressBO;
import lombok.Data;

import java.io.Serializable;


@Data
public class InfUserEditDTO implements Serializable {
    private Long id;
    private String generalIntroduction;
    private String socialLinks;
    private InfUserAddressBO userAddress;
}
