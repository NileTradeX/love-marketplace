package com.love.marketplace.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(value= JsonInclude.Include.NON_EMPTY)
public class BoltCreateOrderTokenRequestDTO {
    @JsonProperty("cart")
    @SerializedName("cart")
    private CartDTO cart;
    @JsonProperty("channel")
    @SerializedName("channel")
    private String channel;
    @JsonProperty("create_cart_on_merchant_backend")
    @SerializedName("create_cart_on_merchant_backend")
    private Boolean createCartOnMerchantBackend;
    @JsonProperty("metadata")
    @SerializedName("metadata")
    private BoltMetadataDTO metadata;
    @JsonProperty("user_note")
    @SerializedName("user_note")
    private String userNote;

    @NoArgsConstructor
    @Data
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    public static class CartDTO {
        @JsonProperty("add_ons")
        @SerializedName("add_ons")
        private List<AddOnsDTO> addOns;
        @JsonProperty("billing_address")
        @SerializedName("billing_address")
        private BoltAddressDTO billingAddress;
        @JsonProperty("discounts")
        @SerializedName("discounts")
        private List<DiscountsDTO> discounts;
        @JsonProperty("fees")
        @SerializedName("fees")
        private List<FeesDTO> fees;
        @JsonProperty("fulfillments")
        @SerializedName("fulfillments")
        private List<FulfillmentsDTO> fulfillments;
        @JsonProperty("in_store_cart_shipments")
        @SerializedName("in_store_cart_shipments")
        private List<InStoreCartShipmentsDTO> inStoreCartShipments;
        @JsonProperty("items")
        @SerializedName("items")
        private List<ItemsDTO> items;
        @JsonProperty("loyalty_rewards")
        @SerializedName("loyalty_rewards")
        private List<LoyaltyRewardsDTO> loyaltyRewards;
        @JsonProperty("shipments")
        @SerializedName("shipments")
        private List<ShipmentDTO> shipments;
        @JsonProperty("tax_amount")
        @SerializedName("tax_amount")
        private Integer taxAmount;
        @JsonProperty("total_amount")
        @SerializedName("total_amount")
        private Integer totalAmount;
        @JsonProperty("cart_url")
        @SerializedName("cart_url")
        private String cartUrl;
        @JsonProperty("currency")
        @SerializedName("currency")
        private String currency;
        @JsonProperty("display_id")
        @SerializedName("display_id")
        private String displayId;
        @JsonProperty("metadata")
        @SerializedName("metadata")
        private BoltMetadataDTO metadata;
        @JsonProperty("order_description")
        @SerializedName("order_description")
        private String orderDescription;
        @JsonProperty("order_reference")
        @SerializedName("order_reference")
        private String orderReference;

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class AddOnsDTO {
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("imageUrl")
            @SerializedName("imageUrl")
            private String imageUrl;
            @JsonProperty("name")
            @SerializedName("name")
            private String name;
            @JsonProperty("price")
            @SerializedName("price")
            private Integer price;
            @JsonProperty("productId")
            @SerializedName("productId")
            private String productId;
            @JsonProperty("productPageUrl")
            @SerializedName("productPageUrl")
            private String productPageUrl;
        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class DiscountsDTO {
            @JsonProperty("amount")
            @SerializedName("amount")
            private Integer amount;
            @JsonProperty("code")
            @SerializedName("code")
            private String code;
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("details_url")
            @SerializedName("details_url")
            private String detailsUrl;
            @JsonProperty("discount_category")
            @SerializedName("discount_category")
            private String discountCategory;
            @JsonProperty("reference")
            @SerializedName("reference")
            private String reference;
            @JsonProperty("type")
            @SerializedName("type")
            private String type;
        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class FeesDTO {
            @JsonProperty("reference")
            @SerializedName("reference")
            private String reference;
            @JsonProperty("name")
            @SerializedName("name")
            private String name;
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("unit_price")
            @SerializedName("unit_price")
            private Integer unitPrice;
            @JsonProperty("unit_tax_amount")
            @SerializedName("unit_tax_amount")
            private Integer unitTaxAmount;
            @JsonProperty("quantity")
            @SerializedName("quantity")
            private Integer quantity;
        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class FulfillmentsDTO {
            @JsonProperty("cart_items")
            @SerializedName("cart_items")
            private List<ItemsDTO> cartItems;
            @JsonProperty("cart_shipment")
            @SerializedName("cart_shipment")
            private ShipmentDTO cartShipment;
            @JsonProperty("digital_delivery")
            @SerializedName("digital_delivery")
            private BoltAddressDTO digitalDelivery;
            @JsonProperty("in_store_cart_shipment")
            @SerializedName("in_store_cart_shipment")
            private InStoreCartShipmentsDTO inStoreCartShipment;
            @JsonProperty("type")
            @SerializedName("type")
            private String type;

        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class InStoreCartShipmentsDTO {
            @JsonProperty("cart_shipment")
            @SerializedName("cart_shipment")
            private ShipmentDTO cartShipment;
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("distance")
            @SerializedName("distance")
            private Integer distance;
            @JsonProperty("distance_unit")
            @SerializedName("distance_unit")
            private String distanceUnit;
            @JsonProperty("in_store_pickup_address")
            @SerializedName("in_store_pickup_address")
            private BoltAddressDTO inStorePickupAddress;
            @JsonProperty("pickup_window_close")
            @SerializedName("pickup_window_close")
            private Integer pickupWindowClose;
            @JsonProperty("pickup_window_open")
            @SerializedName("pickup_window_open")
            private Integer pickupWindowOpen;
            @JsonProperty("store_name")
            @SerializedName("store_name")
            private String storeName;

        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class ItemsDTO {
            @JsonProperty("brand")
            @SerializedName("brand")
            private String brand;
            @JsonProperty("category")
            @SerializedName("category")
            private String category;
            @JsonProperty("collections")
            @SerializedName("collections")
            private List<String> collections;
            @JsonProperty("color")
            @SerializedName("color")
            private String color;
            @JsonProperty("customizations")
            @SerializedName("customizations")
            private List<BoltCustomizationsDTO> customizations;
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("details_url")
            @SerializedName("details_url")
            private String detailsUrl;
            @JsonProperty("external_inputs")
            @SerializedName("external_inputs")
            private ExternalInputsDTO externalInputs;
            @JsonProperty("gift_option")
            @SerializedName("gift_option")
            private BoltGiftOptionDTO giftOption;
            @JsonProperty("image_url")
            @SerializedName("image_url")
            private String imageUrl;
            @JsonProperty("isbn")
            @SerializedName("isbn")
            private String isbn;
            @JsonProperty("item_group")
            @SerializedName("item_group")
            private String itemGroup;
            @JsonProperty("manufacturer")
            @SerializedName("manufacturer")
            private String manufacturer;
            @JsonProperty("merchant_product_id")
            @SerializedName("merchant_product_id")
            private String merchantProductId;
            @JsonProperty("merchant_variant_id")
            @SerializedName("merchant_variant_id")
            private String merchantVariantId;
            @JsonProperty("msrp")
            @SerializedName("msrp")
            private Integer msrp;
            @JsonProperty("name")
            @SerializedName("name")
            private String name;
            @JsonProperty("options")
            @SerializedName("options")
            private String options;
            @JsonProperty("properties")
            @SerializedName("properties")
            private List<PropertiesDTO> properties;
            @JsonProperty("quantity")
            @SerializedName("quantity")
            private Integer quantity;
            @JsonProperty("reference")
            @SerializedName("reference")
            private String reference;
            @JsonProperty("shipment")
            @SerializedName("shipment")
            private ShipmentDTO shipment;
            @JsonProperty("shipment_type")
            @SerializedName("shipment_type")
            private String shipmentType;
            @JsonProperty("size")
            @SerializedName("size")
            private String size;
            @JsonProperty("sku")
            @SerializedName("sku")
            private String sku;
            @JsonProperty("source")
            @SerializedName("source")
            private String source;
            @JsonProperty("tags")
            @SerializedName("tags")
            private String tags;
            @JsonProperty("tax_amount")
            @SerializedName("tax_amount")
            private Integer taxAmount;
            @JsonProperty("tax_code")
            @SerializedName("tax_code")
            private String taxCode;
            @JsonProperty("taxable")
            @SerializedName("taxable")
            private Boolean taxable;
            @JsonProperty("total_amount")
            @SerializedName("total_amount")
            private Integer totalAmount;
            @JsonProperty("type")
            @SerializedName("type")
            private String type;
            @JsonProperty("unit_price")
            @SerializedName("unit_price")
            private Integer unitPrice;
            @JsonProperty("uom")
            @SerializedName("uom")
            private String uom;
            @JsonProperty("upc")
            @SerializedName("upc")
            private Long upc;
            @JsonProperty("weight")
            @SerializedName("weight")
            private Integer weight;
            @JsonProperty("weight_unit")
            @SerializedName("weight_unit")
            private String weightUnit;

            @NoArgsConstructor
            @Data
            @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
            public static class ExternalInputsDTO {
                @JsonProperty("shopify_line_item_reference")
                @SerializedName("shopify_line_item_reference")
                private Integer shopifyLineItemReference;
                @JsonProperty("shopify_product_reference")
                @SerializedName("shopify_product_reference")
                private Integer shopifyProductReference;
                @JsonProperty("shopify_product_variant_reference")
                @SerializedName("shopify_product_variant_reference")
                private Integer shopifyProductVariantReference;
            }

            @NoArgsConstructor
            @Data
            @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
            public static class PropertiesDTO {
                @JsonProperty("color")
                @SerializedName("color")
                private String color;
                @JsonProperty("display")
                @SerializedName("display")
                private Boolean display;
                @JsonProperty("name")
                @SerializedName("name")
                private String name;
                @JsonProperty("name_id")
                @SerializedName("name_id")
                private Integer nameId;
                @JsonProperty("value")
                @SerializedName("value")
                private String value;
                @JsonProperty("value_id")
                @SerializedName("value_id")
                private Integer valueId;
            }
        }

        @NoArgsConstructor
        @Data
        @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
        public static class LoyaltyRewardsDTO {
            @JsonProperty("amount")
            @SerializedName("amount")
            private Integer amount;
            @JsonProperty("coupon_code")
            @SerializedName("coupon_code")
            private String couponCode;
            @JsonProperty("description")
            @SerializedName("description")
            private String description;
            @JsonProperty("details")
            @SerializedName("details")
            private String details;
            @JsonProperty("points")
            @SerializedName("points")
            private Integer points;
            @JsonProperty("source")
            @SerializedName("source")
            private String source;
            @JsonProperty("type")
            @SerializedName("type")
            private String type;
        }


    }
    @NoArgsConstructor
    @Data
    @JsonInclude(value= JsonInclude.Include.NON_EMPTY)
    public static class ShipmentDTO {
        @JsonProperty("carrier")
        @SerializedName("carrier")
        private String carrier;
        @JsonProperty("cost")
        @SerializedName("cost")
        private Integer cost;
        @JsonProperty("discounted_by_membership")
        @SerializedName("discounted_by_membership")
        private Boolean discountedByMembership;
        @JsonProperty("estimated_delivery_date")
        @SerializedName("estimated_delivery_date")
        private String estimatedDeliveryDate;
        @JsonProperty("expedited")
        @SerializedName("expedited")
        private Boolean expedited;
        @JsonProperty("package_depth")
        @SerializedName("package_depth")
        private Integer packageDepth;
        @JsonProperty("package_dimension_unit")
        @SerializedName("package_dimension_unit")
        private String packageDimensionUnit;
        @JsonProperty("package_height")
        @SerializedName("package_height")
        private Integer packageHeight;
        @JsonProperty("package_type")
        @SerializedName("package_type")
        private String packageType;
        @JsonProperty("package_weight_unit")
        @SerializedName("package_weight_unit")
        private String packageWeightUnit;
        @JsonProperty("package_width")
        @SerializedName("package_width")
        private Integer packageWidth;
        @JsonProperty("service")
        @SerializedName("service")
        private String service;
        @JsonProperty("shipping_address")
        @SerializedName("shipping_address")
        private BoltAddressDTO shippingAddress;
        @JsonProperty("shipping_address_id")
        @SerializedName("shipping_address_id")
        private String shippingAddressId;
        @JsonProperty("shipping_method")
        @SerializedName("shipping_method")
        private String shippingMethod;
        @JsonProperty("signature")
        @SerializedName("signature")
        private String signature;
        @JsonProperty("tax_amount")
        @SerializedName("tax_amount")
        private Integer taxAmount;
        @JsonProperty("tax_code")
        @SerializedName("tax_code")
        private String taxCode;
        @JsonProperty("total_weight")
        @SerializedName("total_weight")
        private Integer totalWeight;
        @JsonProperty("total_weight_unit")
        @SerializedName("total_weight_unit")
        private String totalWeightUnit;
        @JsonProperty("type")
        @SerializedName("type")
        private String type;
    }

}
