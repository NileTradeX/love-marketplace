package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class MerUserEditBO implements Serializable {
    private Long id;
    private String username;
    private Long roleId;
}
