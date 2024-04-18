package com.love.marketplace.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ShoppingCartVO implements Serializable {

    private List<Brand> brands;

    @Data
    public static class Brand implements Serializable {
        private Long id;
        private String name;
        private String logo;
        private Long merchantId;
        private String slug;
        private List<Item> goodsList;
    }

    @Data
    public static class Item implements Serializable {
        private Long id;
        private Long goodsId;
        private String title;
        private String slug;
        private Long merchantId;
        private Integer status;
        private Long firstCateId;
        private Long secondCateId;
        private String influencerCode;
        private Sku sku;

        @Data
        public static class Sku implements Serializable {
            private Long id;
            private BigDecimal price;
            private Integer stock;
            private String attrValue;
            private String cover;
            private Integer qty;
            private Integer status;
        }
    }


}
