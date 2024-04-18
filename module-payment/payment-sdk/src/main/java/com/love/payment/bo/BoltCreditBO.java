package com.love.payment.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoltCreditBO implements Serializable {
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("skip_hook_notification")
    private Boolean skipHookNotification;
    @JsonProperty("transaction_reference")
    private String transactionReference;
}
