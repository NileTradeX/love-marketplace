package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mer_user_role")
public class MerUserRole implements Serializable {
    private Long userId;
    private Long roleId;
}
