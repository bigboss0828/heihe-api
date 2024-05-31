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
 * @title: BlackEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:31
 * @Version 1.0
 */
@TableName("t_car_black")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车场编码
     */
    @TableField(value = "park_code")
    private String parkCode;

    /**
     * obu
     */
    @TableField(value = "obu_id")
    private String obuId;

    /**
     * 车牌（无颜色 例：苏A12345）
     */
    @TableField(value = "plate")
    private String plate;

    /**
     * 车牌颜色（0：蓝色车牌 1：绿色车牌 2：黄色车牌 3：黑色车牌 4：白色车牌 5：红色车牌 6：渐变绿色 7：黄绿双拼 8：蓝白渐变 -1：未确定）
     */
    @TableField(value = "plate_color")
    private String plateColor;

    /**
     * 车辆类型（0：小车 1：中型车 2：大型车）
     */
    @TableField(value = "car_type")
    private String carType;

    /**
     * 车主名称
     */
    @TableField(value = "owner_name")
    private String ownerName;

    /**
     * 车主电话
     */
    @TableField(value = "owner_phone")
    private String ownerPhone;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 原因
     */
    @TableField(value = "reason")
    private String reason;

}
