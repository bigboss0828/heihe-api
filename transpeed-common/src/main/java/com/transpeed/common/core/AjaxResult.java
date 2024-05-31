package com.transpeed.common.core;

import com.transpeed.common.constant.system.HttpStatus;
import com.transpeed.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hanshuai
 * @title: AjaxResult
 * @description: 通用返回实体
 * @date 2023/5/25 16:05
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";

    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";

    public static final String DATA_LIST = "list";

    /**
     * 数据总条数
     */
    public static final String DATA_TOTAL = "total";

    /**
     * 页面容量
     */
    public static final String DATA_PAGE_SIZE = "pageSize";

    /**
     * 当前页数
     */
    public static final String DATA_PAGE_NUM = "pageNum";

    /**
     * 初始化一个空对象 使其表示一个空消息
     */
    public AjaxResult() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code  状态码
     * @param msg   返回内容
     * @param data  数据对象
     * @param total 数据总条数
     */
    public AjaxResult(int code, String msg, Object data, Long total, Long pageSize, Long pageNum) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        Map<String, Object> resultData = new HashMap<>();
        resultData.put(DATA_LIST, data);
        resultData.put(DATA_TOTAL, total);
        resultData.put(DATA_PAGE_SIZE, pageSize);
        resultData.put(DATA_PAGE_NUM, pageNum);
        super.put(DATA_TAG, resultData);
    }

    /**
     * 初始化一个新创建得AjaxResult对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建得AjaxResult对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @param data 成功数据
     * @return 成功消息
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data, Long total, Long pageSize, Long pageNum) {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data, total, pageSize, pageNum);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key   键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
