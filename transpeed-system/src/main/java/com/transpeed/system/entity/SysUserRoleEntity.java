package com.transpeed.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysUserRole
 * @description: 用户角色关联表
 * @date 2023/6/1 14:33
 */
@TableName(value = "sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoleEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;

}
