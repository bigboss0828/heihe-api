package com.transpeed.common.constant.system;

/**
 * @author hanshuai
 * @title: SystemStatus
 * @description: 系统状态  正常、停用
 * @date 2023/6/15 14:16
 */
public enum SystemStatus {

    OK(0, "正常"), DISABLE(1, "停用");

    private final Integer code;
    private final String info;

    SystemStatus(Integer code, String info) {
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
