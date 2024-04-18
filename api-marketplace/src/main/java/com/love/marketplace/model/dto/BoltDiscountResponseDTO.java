package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltDiscountResponseDTO extends BoltEventDTO {
    @SerializedName("discount_code")
    private String discountCode;
    @SerializedName("description")
    private String description;
    @SerializedName("discount_type")
    private DiscountType discountType;
    /**
     * The amount in cents. Nullable for Transactions Details.
     */
    @SerializedName("discount_amount")
    private BigDecimal discountAmount;
    @SerializedName("category")
    private String category;
    enum DiscountType{
        FIXED_AMOUNT("fixed_amount"),
        PERCENTAGE("percentage"),
        SHIPPING("shipping"),
        ;
        private final String value;
        DiscountType(String value){
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }
    }
    public BoltDiscountResponseDTO(String discountCode){
        this.discountCode = discountCode;
        this.description = discountCode;
        this.discountType = BoltDiscountResponseDTO.DiscountType.FIXED_AMOUNT;
        this.discountAmount = BigDecimal.ZERO;
        this.category = discountType.value;
    }
}
