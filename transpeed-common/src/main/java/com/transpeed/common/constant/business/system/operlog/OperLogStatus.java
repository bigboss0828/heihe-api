package com.transpeed.common.constant.business.system.operlog;

/**
 * @author hanshuai
 * @title: OperLogStatus
 * @description:
 * @date 2023/6/19 10:26
 */
public enum OperLogStatus {

    SUCCESS(0, "操作成功"), FAIL(1, "操作失败");

    private final Integer code;
    private final String info;

    OperLogStatus(Integer code, String info) {
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
