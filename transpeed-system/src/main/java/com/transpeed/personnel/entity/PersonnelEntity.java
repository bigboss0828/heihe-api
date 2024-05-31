package com.transpeed.personnel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.annotation.Excel;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: PersonnelEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:20
 * @Version 1.0
 */
@TableName("p_person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelEntity extends BaseEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车场编码
     */
    @TableField("park_code")
    private String parkCode;

    /**
     * 人员姓名
     */
    @Excel(name = "人员名称")
    @TableField("owner_name")
    private String ownerName;

    /**
     * 车主电话
     */
    @Excel(name = "手机号")
    @TableField("owner_phone")
    private String ownerPhone;

    /**
     * 身份证号
     */
    @Excel(name = "身份证")
    @TableField("id_card")
    private String idCard;

    /**
     * IC卡号
     */
    @Excel(name = "IC卡号")
    @TableField("ic_no")
    private String icNo;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("end_time")
    private Date endTime;

    /**
     * 人员等级（p_person_level的type_key字段）
     */
    @TableField("person_level")
    private String personLevel;

    /**
     * IC卡状态(0-正常 1-禁用)
     */
    @TableField("ic_status")
    private String icStatus;

    @TableField("depart_name")
    private String departName;

    @TableField("company")
    private String company;

}
