package com.transpeed.personnel.common.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.constant.system.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @title: PersonnelUIDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 17:20
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelUIDto {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 停车场编码
     */
    private String parkCode;

    /**
     * 人员姓名
     */
    @NotBlank(message = "人员姓名不能为空！")
    private String ownerName;

    /**
     * 车主电话
     */
    private String ownerPhone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * IC卡号
     */
    @NotBlank(message = "ic卡号不能为空")
    private String icNo;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /**
     * 人员等级（p_person_level的type_key字段）
     */
    @NotBlank(message = "人员等级不能为空")
    private String personLevel;

    /**
     * IC卡状态(0-正常 1-禁用)
     */
    @NotBlank(message = "IC卡状态不能为空")
    private String icStatus;

    private String departName;

    private String company;

}
