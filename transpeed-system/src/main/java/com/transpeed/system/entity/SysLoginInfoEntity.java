package com.transpeed.system.entity;

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

/**
 * @author hanshuai
 * @title: SysLoginInfo
 * @description: 登录日志实体
 * @date 2023/6/1 09:55
 */
@TableName(value = "sys_login_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginInfoEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long loginInfoId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 登录状态（0:登录成功  1:登录失败）
     */
    @TableField("login_status")
    private Integer loginStatus;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("login_time")
    private Date loginTime;

    /**
     * 浏览器
     */
    @TableField("login_browser")
    private String loginBrowser;

    /**
     * 操作系统
     */
    @TableField("login_os")
    private String loginOs;

}
