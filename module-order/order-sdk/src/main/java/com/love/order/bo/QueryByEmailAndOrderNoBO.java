package com.love.order.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryByEmailAndOrderNoBO implements Serializable {

    private String merOrderNo;

    private String email;

    private Long userId;
}
