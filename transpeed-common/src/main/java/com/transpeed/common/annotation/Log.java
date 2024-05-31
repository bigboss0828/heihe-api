package com.transpeed.common.annotation;

import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;

import java.lang.annotation.*;

/**
 * @author hanshuai
 * @title: Log
 * @description: 自定义操作日志记录注解
 * @date 2023/6/5 14:08
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    BusinessTitle title() default BusinessTitle.UNKNOWN;

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

}
