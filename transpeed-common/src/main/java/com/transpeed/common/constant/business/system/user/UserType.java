package com.transpeed.common.constant.business.system.user;

/**
 * @author hanshuai
 * @title: UserType
 * @description: 用户类型
 * @date 2023/6/14 17:54
 */
public enum UserType {

    ADMIN(0, "超级管理员"), PARK(1, "车场管理员"), COMPANY(2, "车场管理员");

    private final Integer code;
    private final String info;

    UserType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
