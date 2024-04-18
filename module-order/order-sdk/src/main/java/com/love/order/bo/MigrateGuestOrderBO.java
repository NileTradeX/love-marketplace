package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MigrateGuestOrderBO implements Serializable {
    private Long guestId;
    private Long userId;
    private String buyerName;
}
