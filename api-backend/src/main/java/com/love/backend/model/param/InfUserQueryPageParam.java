package com.love.backend.model.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfUserQueryPageParam implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String account;
    private String username;
}
