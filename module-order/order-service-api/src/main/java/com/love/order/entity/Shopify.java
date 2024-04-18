package com.love.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("authorized_client")
public class Shopify implements Serializable {
    private String clientRegistrationId;
    @TableId
    private String principalName;
    private LocalDateTime accessTokenExpiresAt;
    private LocalDateTime accessTokenIssuedAt;
    private String accessTokenType;
    private String accessTokenValue;
    private LocalDateTime createdAt;
}