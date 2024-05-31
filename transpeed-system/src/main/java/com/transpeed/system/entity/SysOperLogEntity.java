package com.transpeed.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hanshuai
 * @title: SysOperLog
 * @description: 操作日志实体
 * @date 2023/6/19 10:16
 */
@TableName(value = "sys_oper_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLogEntity extends BaseEntity {

    /**
     * 日志主键
     */
    @TableField("oper_id")
    private Long operId;

    /**
     * 模块标题
     */
    @TableField("title")
    private Integer title;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 操作人员
     */
    @TableField("oper_name")
    private String operName;

    /**
     * 请求URL
     */
    @TableField("oper_url")
    private String operUrl;

    /**
     * 请求参数
     */
    @TableField("oper_param")
    private String operParam;

    /**
     * 返回参数
     */
    @TableField("json_result")
    private String jsonResult;

    /**
     * 操作状态（0:成功  1:失败）
     */
    @TableField("oper_status")
    private Integer operStatus;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("oper_time")
    private Date operTime;

}
