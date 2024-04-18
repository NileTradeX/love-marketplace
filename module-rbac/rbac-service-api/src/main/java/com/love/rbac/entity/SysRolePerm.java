package com.love.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role_perm")
public class SysRolePerm implements Serializable {
    private Long roleId;
    private Long permId;
}
