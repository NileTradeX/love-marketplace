package com.love.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaymentRefundDetailDTO implements Serializable {
    @JsonProperty("id")
    private String id;

    @JsonProperty("refund_transactions")
    private List<RefundTransactionDTO> refundTransactions;

    @Data
    public static class RefundTransactionDTO {
        @JsonProperty("id")
        private String id;

        @JsonProperty("credit")
        private CreditDTO credit;

        @Data
        public static class CreditDTO {
            @JsonProperty("status")
            private String status;
        }
    }
}
