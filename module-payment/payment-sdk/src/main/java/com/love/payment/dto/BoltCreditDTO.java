package com.love.payment.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BoltCreditDTO implements Serializable {
    @JsonProperty("amount")
    private AmountDTO amountDTO;
    @JsonProperty("credit")
    private CreditDTO creditDTO;
    @JsonProperty("id")
    private String id;

    @Data
    public static class AmountDTO {
        @JsonProperty("amount")
        private Long amount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("currencySymbol")
        private String currency_symbol;
    }

    @Data
    public static class CreditDTO {
        @JsonProperty("status")
        private String status;
        @JsonProperty("merchant_event_id")
        private String merchantEventId;
    }
}
