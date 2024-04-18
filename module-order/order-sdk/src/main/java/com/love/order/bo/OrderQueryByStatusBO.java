package com.love.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderQueryByStatusBO implements Serializable {
    private Integer status;
}
