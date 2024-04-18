package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOrderByTrackBO implements Serializable {
    private String carriers;
    private String trackingNo;
    private Long merchantOrderId;
}
