package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class IngredientDTO implements Serializable {
    private String name;
    private String amount;
}
