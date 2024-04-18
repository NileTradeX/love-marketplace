package com.love.marketplace.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(value= JsonInclude.Include.NON_EMPTY)
public class BoltCustomizationsDTO {
    @JsonProperty("attributes")
    private BoltMetadataDTO attributes;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private BoltAmountDTO price;
}
