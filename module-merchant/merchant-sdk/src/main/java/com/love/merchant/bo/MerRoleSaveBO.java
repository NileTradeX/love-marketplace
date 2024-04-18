package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerRoleSaveBO implements Serializable {
    private String name;
    private Long groupId;
}
