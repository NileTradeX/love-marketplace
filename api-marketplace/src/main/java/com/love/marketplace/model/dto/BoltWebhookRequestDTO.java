package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Data
public class BoltWebhookRequestDTO {
    @SerializedName("type")
    private String type;
    @SerializedName("object")
    private String object;
    @SerializedName("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("id")
        private String id;
        @SerializedName("type")
        private String type;
        @SerializedName("processor")
        private String processor;
        @SerializedName("date")
        private Instant date;
        @SerializedName("reference")
        private String reference;
        @SerializedName("status")
        private String status;
        @SerializedName("from_user")
        private FromUserDTO fromUser;
        @SerializedName("from_credit_card")
        private FromCreditCardDTO fromCreditCard;
        @SerializedName("amount")
        private BoltAmountDTO amount;
        @SerializedName("order")
        private OrderDTO order;
        @SerializedName("authorization")
        private AuthorizationDTO authorization;
        @SerializedName("captures")
        private List<CapturesDTO> captures;
        @SerializedName("refund_transactions")
        private List<?> refundTransactions;
        @SerializedName("risk_signals")
        private RiskSignalsDTO riskSignals;
        @SerializedName("capture_type")
        private String captureType;

        @NoArgsConstructor
        @Data
        public static class FromUserDTO {
            @SerializedName("id")
            private String id;
            @SerializedName("first_name")
            private String firstName;
            @SerializedName("last_name")
            private String lastName;
            @SerializedName("phones")
            private List<PhonesDTO> phones;
            @SerializedName("emails")
            private List<EmailsDTO> emails;

            @NoArgsConstructor
            @Data
            public static class PhonesDTO {
                @SerializedName("number")
                private String number;
                @SerializedName("country_code")
                private String countryCode;
            }

            @NoArgsConstructor
            @Data
            public static class EmailsDTO {
                @SerializedName("address")
                private String address;
            }
        }

        @NoArgsConstructor
        @Data
        public static class FromCreditCardDTO {
            @SerializedName("id")
            private String id;
            @SerializedName("last4")
            private String last4;
            @SerializedName("bin")
            private String bin;
            @SerializedName("expiration")
            private Double expiration;
            @SerializedName("network")
            private String network;
            @SerializedName("display_network")
            private String displayNetwork;
            @SerializedName("token_type")
            private String tokenType;
            @SerializedName("status")
            private String status;
            @SerializedName("billing_address")
            private BoltAddressDTO billingAddress;
        }

        @NoArgsConstructor
        @Data
        public static class OrderDTO {
            @SerializedName("cart")
            private CartDTO cart;

            @NoArgsConstructor
            @Data
            public static class CartDTO {
                @SerializedName("order_reference")
                private String orderReference;
                @SerializedName("display_id")
                private String displayId;
                @SerializedName("total_amount")
                private BoltAmountDTO totalAmount;
                @SerializedName("tax_amount")
                private BoltAmountDTO taxAmount;
                @SerializedName("items")
                private List<ItemsDTO> items;
                @SerializedName("shipments")
                private List<ShipmentsDTO> shipments;
                @SerializedName("subtotal_amount")
                private BoltAmountDTO subtotalAmount;
                @SerializedName("shipping_amount")
                private BoltAmountDTO shippingAmount;
                @SerializedName("discount_amount")
                private BoltAmountDTO discountAmount;
                @SerializedName("billing_address")
                private BoltAddressDTO billingAddress;

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
                    private Double quantity;
                    @SerializedName("image_url")
                    private String imageUrl;
                    @SerializedName("taxable")
                    private Boolean taxable;
                    @SerializedName("type")
                    private String type;
                }

                @NoArgsConstructor
                @Data
                public static class ShipmentsDTO {
                    @SerializedName("shipping_address")
                    private BoltAddressDTO shippingAddress;
                    @SerializedName("shipping_method")
                    private String shippingMethod;
                    @SerializedName("service")
                    private String service;
                    @SerializedName("cost")
                    private BoltAmountDTO cost;
                    @SerializedName("tax_amount")
                    private BoltAmountDTO taxAmount;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class AuthorizationDTO {
            @SerializedName("auth")
            private String auth;
            @SerializedName("metadata")
            private MetadataDTO metadata;
            @SerializedName("reason")
            private String reason;
            @SerializedName("status")
            private String status;

            @NoArgsConstructor
            @Data
            public static class MetadataDTO {
                @SerializedName("processor_card_token")
                private String processorCardToken;
                @SerializedName("processor_shopperReference")
                private String processorShopperreference;
                @SerializedName("processor_token_alias")
                private String processorTokenAlias;
            }
        }

        @NoArgsConstructor
        @Data
        public static class RiskSignalsDTO {
            @SerializedName("ip_address")
            private String ipAddress;
            @SerializedName("time_on_site")
            private String timeOnSite;
            @SerializedName("http_headers")
            private HttpHeadersDTO httpHeaders;

            @NoArgsConstructor
            @Data
            public static class HttpHeadersDTO {
                @SerializedName("accept")
                private String accept;
                @SerializedName("accept_encoding")
                private String acceptEncoding;
                @SerializedName("accept_language")
                private String acceptLanguage;
                @SerializedName("connection")
                private String connection;
                @SerializedName("host")
                private String host;
                @SerializedName("referer")
                private String referer;
                @SerializedName("user_agent")
                private String userAgent;
            }
        }

        @NoArgsConstructor
        @Data
        public static class CapturesDTO {
            @SerializedName("id")
            private String id;
            @SerializedName("status")
            private String status;
            @SerializedName("amount")
            private BoltAmountDTO amount;
        }
    }
}
