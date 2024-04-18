package com.love.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandBatchSaveBO implements Serializable {
    private List<BrandSaveBO> brands;
}
