package com.transpeed.system.common.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysUserUpdatePasswordDto
 * @description: 用户修改密码接收前端对象
 * @date 2023/6/14 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserUpdatePasswordDto {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
