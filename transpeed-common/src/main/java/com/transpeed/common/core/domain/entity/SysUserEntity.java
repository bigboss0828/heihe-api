package com.transpeed.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author hanshuai
 * @title: SysUser
 * @description: 用户实体
 * @date 2023/5/26 14:01
 */
@TableName(value = "sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 登陆账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户当前车场
     */
    @TableField("current_park")
    private String currentPark;

    /**
     * 用户来源（0：后台用户 1：移动端用户）
     */
    @TableField("user_source_type")
    private Integer userSourceType;

    /**
     * 用户类型（0：超级管理员 1：车场管理员 2：单位管理员）
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 用户电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 账号状态（0：正常 1：停用 ）
     */
    @TableField("user_status")
    private Integer userStatus;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 拥有权限角色ID
     */
    @TableField(exist = false)
    private List<Integer> roleIdList;

}
