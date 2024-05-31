package com.transpeed.system.common.dto.operlog;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hanshuai
 * @title: SysOperLogDto
 * @description: 操作日志接收前端对象
 * @date 2023/6/19 16:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLogDto extends BaseDto {

    /**
     * 模块标题
     */
    private Integer title;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 操作状态（0:成功  1:失败）
     */
    private Integer operStatus;

    /**
     * 操作时间
     */
    private Date operTime;

}
