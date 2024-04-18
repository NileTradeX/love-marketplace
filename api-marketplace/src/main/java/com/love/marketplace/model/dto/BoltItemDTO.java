package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BoltItemDTO {
    @SerializedName("reference")
    private String reference;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("options")
    private String options;
    @SerializedName("total_amount")
    private Integer totalAmount;
    @SerializedName("unit_price")
    private Integer unitPrice;
    @SerializedName("tax_amount")
    private Integer taxAmount;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("uom")
    private String uom;
    @SerializedName("upc")
    private String upc;
    @SerializedName("sku")
    private String sku;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("brand")
    private String brand;
    @SerializedName("manufacturer")
    private String manufacturer;
    @SerializedName("category")
    private String category;
    @SerializedName("collections")
    private List<String> collections;
    @SerializedName("tags")
    private String tags;
    @SerializedName("color")
    private String color;
    @SerializedName("size")
    private String size;
    @SerializedName("weight")
    private BoltWeightDTO weight;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("details_url")
    private String detailsUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("taxable")
    private Boolean taxable;
    @SerializedName("properties")
    private List<PropertiesDTO> properties;
    @SerializedName("item_group")
    private String itemGroup;
    @SerializedName("shipment_type")
    private String shipmentType;
    @SerializedName("customizations")
    private List<BoltCustomizationsDTO> customizations;
    @SerializedName("bolt_product_id")
    private String boltProductId;
    @SerializedName("gift_options")
    private BoltGiftOptionDTO giftOptions;


    @NoArgsConstructor
    @Data
    public static class PropertiesDTO {
        @SerializedName("name")
        private String name;
        @SerializedName("value")
        private String value;
    }
}
