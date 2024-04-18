package com.love.merchant.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerUserAdminQueryBO implements Serializable {
    private Long userId;
    private boolean simple;
}
