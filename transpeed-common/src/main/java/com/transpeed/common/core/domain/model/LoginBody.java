package com.transpeed.common.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author hanshuai
 * @title: LoginBody
 * @description: 前端登录实体
 * @date 2023/5/25 16:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 唯一标识
     */
    @NotBlank(message = "登陆唯一标识为空，系统异常，请联系管理员处理")
    private String uuid;

}
