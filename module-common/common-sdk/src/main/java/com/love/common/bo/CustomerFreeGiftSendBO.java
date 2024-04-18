package com.love.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFreeGiftSendBO implements Serializable {
    private String promoCode;
    private String promoProductUrl;
    private String toEmail;
}
