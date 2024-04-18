package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class IngredientVO implements Serializable {
    private String name;
    private String amount;
}
