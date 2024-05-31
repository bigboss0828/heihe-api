package com.transpeed.car.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: WhiteTypeEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 11:47
 * @Version 1.0
 */
@TableName("t_car_white_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhiteTypeEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车场编码
     */
    @TableField("park_code")
    private String parkCode;

    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 键
     */
    @TableField("type_key")
    private String typeKey;

    /**
     * 限制开始时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("limit_start")
    private Date limitStart;

    /**
     * 限制结束时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("limit_end")
    private Date limitEnd;

}
