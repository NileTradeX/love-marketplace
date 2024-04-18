package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class BoltShippingOptionDTO {
    @SerializedName("service")
    private String service;
    @SerializedName("description")
    private String description;
    @SerializedName("cost")
    private Integer cost;
    @SerializedName("tax_amount")
    private Integer taxAmount;
    @SerializedName("reference")
    private String reference;
    @SerializedName("signature")
    private String signature;
    @SerializedName("estimated_delivery_date")
    private String estimatedDeliveryDate;
    @SerializedName("tax_code")
    private String taxCode;
    @SerializedName("discount_by_membership")
    private Boolean discountByMembership;
    @SerializedName("default")
    private Boolean defaultX;
    @SerializedName("description_tooltips")
    private List<DescriptionTooltipsDTO> descriptionTooltips;
    @NoArgsConstructor
    @Data
    public static class DescriptionTooltipsDTO {
        @SerializedName("target")
        private Integer target;
        @SerializedName("html_content")
        private String htmlContent;
    }
}
