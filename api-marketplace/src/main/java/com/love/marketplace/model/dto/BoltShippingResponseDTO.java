package com.love.marketplace.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class BoltShippingResponseDTO extends BoltEventDTO {
    @SerializedName("shipping_options")
    private List<BoltShippingOptionDTO> shippingOptions;
    @SerializedName("pickup_options")
    private List<BoltPickupOptionDTO> pickupOptions;
    @SerializedName("ship_to_store_options")
    private List<BoltShipToStoreOptionDTO> shipToStoreOptions;
    public BoltShippingResponseDTO(String service, Integer cost){
        List<BoltShippingOptionDTO> shippingOptionDTOS = new ArrayList<>(1);
        BoltShippingOptionDTO shippingOptionDTO = new BoltShippingOptionDTO();
        shippingOptionDTO.setService(service);
        shippingOptionDTO.setCost(cost);
        shippingOptionDTOS.add(shippingOptionDTO);
        this.shippingOptions = shippingOptionDTOS;
    }
}
