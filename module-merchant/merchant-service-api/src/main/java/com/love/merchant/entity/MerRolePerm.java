package com.love.merchant.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("mer_role_perm")
public class MerRolePerm implements Serializable {
    private Long roleId;
    private Long permId;
}
