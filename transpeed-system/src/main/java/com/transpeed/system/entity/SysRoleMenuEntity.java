package com.transpeed.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysRoleMenu
 * @description: 角色菜单关联实体
 * @date 2023/6/1 14:31
 */
@TableName(value = "sys_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenuEntity {

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Integer menuId;

}
