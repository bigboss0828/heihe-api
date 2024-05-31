package com.transpeed.common.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hanshuai
 * @title: BaseDto
 * @description: 基础数据传输对象
 * @date 2023/6/5 11:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {

    /**
     * 页码 默认1
     */
    private Integer pageNum = 1;

    /**
     * 每页显示条数 默认10
     */
    private Integer pageSize = 10;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 备注
     */
    private String remark;

}
