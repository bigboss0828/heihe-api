package com.transpeed.system.common.dto.user;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author hanshuai
 * @title: SysUserDto
 * @description: 用户接收前端对象
 * @date 2023/6/9 15:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDto extends BaseDto {

    /**
     * 主键ID
     */
    private Integer userId;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 登陆账号
     */
    @NotBlank(message = "登录账号不能为空")
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    /**
     * 用户当前车场
     */
    private String currentPark;

    /**
     * 用户来源（0：后台用户 1：移动端用户）
     */
    @NotNull(message = "用户来源不能为空")
    private Integer userSourceType;

    /**
     * 用户类型（0：超级管理员 1：车场管理员 2：单位管理员）
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 账号状态（0：正常 1：停用 ）
     */
    private Integer userStatus;

    /**
     * 用户角色权限
     */
    @NotNull(message = "")
    private List<Integer> roleIdList;

}
