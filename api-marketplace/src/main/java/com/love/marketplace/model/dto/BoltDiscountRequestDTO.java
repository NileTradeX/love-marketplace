package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltDiscountRequestDTO extends BoltEventDTO {
    @SerializedName("discount_code")
    private String discountCode;
    @SerializedName("cart")
    private CartDTO cart;
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("customer_email")
    private String customerEmail;
    @SerializedName("customer_phone")
    private String customerPhone;
    @NoArgsConstructor
    @Data
    public static class CartDTO {
        @SerializedName("order_reference")
        private String orderReference;
        @SerializedName("display_id")
        private String displayId;
        @SerializedName("currency")
        private BoltCurrencyDTO currency;
        @SerializedName("subtotal_amount")
        private BoltAmountDTO subtotalAmount;
        @SerializedName("total_amount")
        private BoltAmountDTO totalAmount;
        @SerializedName("tax_amount")
        private BoltAmountDTO taxAmount;
        @SerializedName("shipping_amount")
        private BoltAmountDTO shippingAmount;
        @SerializedName("discount_amount")
        private BoltAmountDTO discountAmount;
        @SerializedName("billing_address")
        private BoltAddressDTO billingAddress;
        @SerializedName("items")
        private List<BoltItemDTO> items;
        @SerializedName("shipments")
        private List<BoltShipmentDTO> shipments;
        @SerializedName("discounts")
        private List<String> discounts;
    }
}
