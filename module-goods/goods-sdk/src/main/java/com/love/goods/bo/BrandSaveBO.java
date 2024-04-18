package com.love.goods.bo;

import com.love.goods.enums.BrandStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class BrandSaveBO implements Serializable {
    private Long id;
    private Long merchantId;
    private String name;
    private String logo;
    private Integer status = BrandStatus.DISABLE.getStatus();
}
