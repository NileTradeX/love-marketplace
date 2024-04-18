package com.love.merchant.bo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SettingPermsBO implements Serializable {
    private Long roleId;
    private List<Long> permIds;
}
