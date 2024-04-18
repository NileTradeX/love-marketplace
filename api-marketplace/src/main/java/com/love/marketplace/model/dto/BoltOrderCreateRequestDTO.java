package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltOrderCreateRequestDTO extends BoltEventDTO {
    @SerializedName("order")
    private OrderDTO order;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("currency")
    private String currency;

    @NoArgsConstructor
    @Data
    public static class OrderDTO {
        @SerializedName("token")
        private String token;
        @SerializedName("cart")
        private CartDTO cart;

        @NoArgsConstructor
        @Data
        public static class CartDTO {
            @SerializedName("display_id")
            private String displayId;
            @SerializedName("order_reference")
            private String orderReference;
            @SerializedName("transaction_reference")
            private String transactionReference;
            @SerializedName("currency")
            private BoltCurrencyDTO currency;
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
            private List<ItemsDTO> items;
            @SerializedName("shipments")
            private List<ShipmentsDTO> shipments;

            @NoArgsConstructor
            @Data
            public static class ItemsDTO {
                @SerializedName("reference")
                private String reference;
                @SerializedName("name")
                private String name;
                @SerializedName("total_amount")
                private BoltAmountDTO totalAmount;
                @SerializedName("unit_price")
                private BoltAmountDTO unitPrice;
                @SerializedName("tax_amount")
                private BoltAmountDTO taxAmount;
                @SerializedName("quantity")
                private Integer quantity;
                @SerializedName("image_url")
                private String imageUrl;
                @SerializedName("type")
                private String type;
                @SerializedName("taxable")
                private Boolean taxable;
                @SerializedName("shipment_type")
                private String shipmentType;
            }

            @NoArgsConstructor
            @Data
            public static class ShipmentsDTO {
                @SerializedName("id")
                private String id;
                @SerializedName("shipping_address")
                private BoltAddressDTO shippingAddress;
                @SerializedName("shipping_method")
                private String shippingMethod;
                @SerializedName("service")
                private String service;
                @SerializedName("cost")
                private BoltAmountDTO cost;
            }
        }
    }
}
