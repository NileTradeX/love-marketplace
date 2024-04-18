package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BoltCreateOrderTokenResponseDTO {
    @SerializedName("cart")
    private CartDTO cart;
    @SerializedName("dynamic_content")
    private DynamicContentDTO dynamicContent;
    @SerializedName("external_data")
    private ExternalDataDTO externalData;
    @SerializedName("token")
    private String token;
    @SerializedName("user_note")
    private String userNote;

    @NoArgsConstructor
    @Data
    public static class CartDTO {
        @SerializedName("billing_address")
        private BoltAddressDTO billingAddress;
        @SerializedName("cart_url")
        private String cartUrl;
        @SerializedName("currency")
        private BoltCurrencyDTO currency;
        @SerializedName("discount_amount")
        private BoltAmountDTO discountAmount;
        @SerializedName("discounts")
        private List<BoltDiscountDTO> discounts;
        @SerializedName("display_id")
        private String displayId;
        @SerializedName("fee_amount")
        private BoltAmountDTO feeAmount;
        @SerializedName("fees")
        private List<FeesDTO> fees;
        @SerializedName("items")
        private List<ItemsDTO> items;
        @SerializedName("merchant_order_url")
        private String merchantOrderUrl;
        @SerializedName("order_description")
        private String orderDescription;
        @SerializedName("order_reference")
        private String orderReference;
        @SerializedName("shipments")
        private List<ShipmentsDTO> shipments;
        @SerializedName("shipping_amount")
        private BoltAmountDTO shippingAmount;
        @SerializedName("subtotal_amount")
        private BoltAmountDTO subtotalAmount;
        @SerializedName("tax_amount")
        private BoltAmountDTO taxAmount;
        @SerializedName("total_amount")
        private BoltAmountDTO totalAmount;
        @SerializedName("transaction_reference")
        private String transactionReference;

        @NoArgsConstructor
        @Data
        public static class FeesDTO {
            @SerializedName("reference")
            private String reference;
            @SerializedName("name")
            private String name;
            @SerializedName("description")
            private String description;
            @SerializedName("unit_price")
            private BoltAmountDTO unitPrice;
            @SerializedName("unit_tax_amount")
            private BoltAmountDTO unitTaxAmount;
            @SerializedName("quantity")
            private Integer quantity;
        }

        @NoArgsConstructor
        @Data
        public static class ItemsDTO {
            @SerializedName("bolt_product_id")
            private String boltProductId;
            @SerializedName("brand")
            private String brand;
            @SerializedName("category")
            private String category;
            @SerializedName("collections")
            private List<String> collections;
            @SerializedName("color")
            private String color;
            @SerializedName("customizations")
            private List<BoltCustomizationsDTO> customizations;
            @SerializedName("description")
            private String description;
            @SerializedName("details_url")
            private String detailsUrl;
            @SerializedName("gift_option")
            private GiftOptionDTO giftOption;
            @SerializedName("hide")
            private Boolean hide;
            @SerializedName("image_url")
            private String imageUrl;
            @SerializedName("isbn")
            private Long isbn;
            @SerializedName("item_group")
            private String itemGroup;
            @SerializedName("manufacturer")
            private String manufacturer;
            @SerializedName("merchant_product_id")
            private String merchantProductId;
            @SerializedName("merchant_variant_id")
            private String merchantVariantId;
            @SerializedName("msrp")
            private BoltAmountDTO msrp;
            @SerializedName("name")
            private String name;
            @SerializedName("options")
            private String options;
            @SerializedName("properties")
            private List<PropertiesDTO> properties;
            @SerializedName("quantity")
            private Integer quantity;
            @SerializedName("reference")
            private String reference;
            @SerializedName("shipment_id")
            private String shipmentId;
            @SerializedName("shipment_type")
            private String shipmentType;
            @SerializedName("shopify_line_item_reference")
            private Integer shopifyLineItemReference;
            @SerializedName("shopify_product_reference")
            private Integer shopifyProductReference;
            @SerializedName("shopify_product_variant_reference")
            private Integer shopifyProductVariantReference;
            @SerializedName("size")
            private String size;
            @SerializedName("sku")
            private String sku;
            @SerializedName("subscription")
            private SubscriptionDTO subscription;
            @SerializedName("tags")
            private String tags;
            @SerializedName("tax_amount")
            private BoltAmountDTO taxAmount;
            @SerializedName("taxable")
            private Boolean taxable;
            @SerializedName("total_amount")
            private BoltAmountDTO totalAmount;
            @SerializedName("type")
            private String type;
            @SerializedName("unit_price")
            private BoltAmountDTO unitPrice;
            @SerializedName("uom")
            private String uom;
            @SerializedName("upc")
            private Long upc;
            @SerializedName("weight")
            private BoltWeightDTO weight;

            @NoArgsConstructor
            @Data
            public static class GiftOptionDTO {
                @SerializedName("hide_gift_message")
                private Boolean hideGiftMessage;
                @SerializedName("hide_gift_wrap")
                private Boolean hideGiftWrap;
            }

            @NoArgsConstructor
            @Data
            public static class SubscriptionDTO {
                @SerializedName("frequency")
                private FrequencyDTO frequency;

                @NoArgsConstructor
                @Data
                public static class FrequencyDTO {
                    @SerializedName("unit")
                    private String unit;
                    @SerializedName("value")
                    private Integer value;
                }
            }

            @NoArgsConstructor
            @Data
            public static class PropertiesDTO {
                @SerializedName("color")
                private String color;
                @SerializedName("display")
                private Boolean display;
                @SerializedName("name")
                private String name;
                @SerializedName("value")
                private String value;
            }
        }

        @NoArgsConstructor
        @Data
        public static class ShipmentsDTO {
            @SerializedName("carrier")
            private String carrier;
            @SerializedName("cost")
            private BoltAmountDTO cost;
            @SerializedName("default")
            private Boolean defaultX;
            @SerializedName("description")
            private List<DescriptionDTO> description;
            @SerializedName("description_tooltips")
            private List<DescriptionTooltipsDTO> descriptionTooltips;
            @SerializedName("estimated_delivery_date")
            private String estimatedDeliveryDate;
            @SerializedName("expedited")
            private Boolean expedited;
            @SerializedName("gift_options")
            private ItemsDTO.GiftOptionDTO giftOptions;
            @SerializedName("id")
            private String id;
            @SerializedName("package_dimension")
            private PackageDimensionDTO packageDimension;
            @SerializedName("package_type")
            private String packageType;
            @SerializedName("package_weight")
            private BoltWeightDTO packageWeight;
            @SerializedName("reference")
            private String reference;
            @SerializedName("service")
            private String service;
            @SerializedName("shipping_address")
            private BoltAddressDTO shippingAddress;
            @SerializedName("shipping_method")
            private String shippingMethod;
            @SerializedName("signature")
            private String signature;
            @SerializedName("tax_amount")
            private BoltAmountDTO taxAmount;
            @SerializedName("total_weight")
            private BoltWeightDTO totalWeight;
            @SerializedName("type")
            private String type;

            @NoArgsConstructor
            @Data
            public static class PackageDimensionDTO {
                @SerializedName("depth")
                private Integer depth;
                @SerializedName("height")
                private Integer height;
                @SerializedName("unit")
                private String unit;
                @SerializedName("width")
                private Integer width;
            }

            @NoArgsConstructor
            @Data
            public static class DescriptionDTO {
                @SerializedName("content")
                private String content;
                @SerializedName("is_html")
                private Boolean isHtml;
            }

            @NoArgsConstructor
            @Data
            public static class DescriptionTooltipsDTO {
                @SerializedName("html_content")
                private String htmlContent;
                @SerializedName("target")
                private Integer target;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class DynamicContentDTO {
        @SerializedName("custom_fields")
        private List<CustomFieldsDTO> customFields;
        @SerializedName("eligible_payment_methods")
        private List<EligiblePaymentMethodsDTO> eligiblePaymentMethods;
        @SerializedName("gift_option_view")
        private CartDTO.ItemsDTO.GiftOptionDTO giftOptionView;
        @SerializedName("hide_apm")
        private List<String> hideApm;
        @SerializedName("order_notice")
        private String orderNotice;
        @SerializedName("payment_notice")
        private String paymentNotice;
        @SerializedName("shipping_info_notice")
        private String shippingInfoNotice;
        @SerializedName("shipping_notice")
        private String shippingNotice;

        @NoArgsConstructor
        @Data
        public static class CustomFieldsDTO {
            @SerializedName("checkout_step")
            private String checkoutStep;
            @SerializedName("dynamic")
            private Boolean dynamic;
            @SerializedName("external_id")
            private String externalId;
            @SerializedName("field_setup")
            private String fieldSetup;
            @SerializedName("helper_text")
            private String helperText;
            @SerializedName("label")
            private String label;
            @SerializedName("position")
            private Integer position;
            @SerializedName("public_id")
            private String publicId;
            @SerializedName("required")
            private Boolean required;
            @SerializedName("subscribeToNewsletter")
            private Boolean subscribeToNewsletter;
        }

        @NoArgsConstructor
        @Data
        public static class EligiblePaymentMethodsDTO {
            @SerializedName("eligible")
            private Boolean eligible;
            @SerializedName("transaction_processor_type")
            private String transactionProcessorType;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ExternalDataDTO {
        @SerializedName("steam_id")
        private String steamId;
    }
}
