package com.transpeed.system.common.dto.logininfo;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysLoginInfoDto
 * @description: 菜单接收前端对象
 * @date 2023/6/16 14:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginInfoDto extends BaseDto {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录状态（0:登录成功  1:登录失败）
     */
    private Integer loginStatus;

}
