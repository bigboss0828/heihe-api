package com.transpeed.common.exception;

/**
 * @author hanshuai
 * @title: UtilException
 * @description: 工具类异常
 * @date 2022/2/21 10:13
 */
public class UtilException extends RuntimeException{

    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
