package com.love.payment.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SplitFundsBO implements Serializable {
    private Long totalAmount;
    private String currency;
    private String pspReference;
    private String merchantReference;
    private List<SplitItem> items;

    @Data
    public static class SplitItem implements Serializable {
        private Long amount;
        private String type;
        private Long merchantId;
        private BigDecimal rate;
        private String reference;
        private String account;
    }
}
