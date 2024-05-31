package com.transpeed.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hanshuai
 * @title: SysRole
 * @description: 角色实体
 * @date 2023/6/1 11:52
 */
@TableName(value = "sys_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色状态（0：正常 1：停用）
     */
    @TableField("role_status")
    private Integer roleStatus;

    /**
     * 拥有权限菜单ID
     */
    @TableField(exist = false)
    private List<Integer> menuIdList;

}
