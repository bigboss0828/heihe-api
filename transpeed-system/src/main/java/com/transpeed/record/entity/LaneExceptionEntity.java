package com.transpeed.record.entity;

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
 * @title: LaneExceptionEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 15:55
 * @Version 1.0
 */
@TableName("t_lane_exception_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaneExceptionEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 车场编码
     */
    @TableField(value = "park_code")
    private String parkCode;

    /**
     * 车牌号码
     */
    @TableField(value = "plate")
    private String plate;

    /**
     * 事物ID
     */
    @TableField(value = "tx_id")
    private String txId;

    /**
     * 进场表id
     */
    @TableField(value = "in_id")
    private Integer inId;

    /**
     * 进场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField(value = "in_time")
    private Date inTime;

    /**
     * 进场url
     */
    @TableField(value = "in_url")
    private String inUrl;

    /**
     * 凭证号
     */
    @TableField(value = "voucher")
    private String voucher;

    /**
     * 凭证类型
     */
    @TableField(value = "voucher_type")
    private String voucherType;

    /**
     * 出场表id
     */
    @TableField(value = "out_id")
    private Integer outId;

    /**
     * 出场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField(value = "out_time")
    private Date outTime;

    /**
     * 出场的url
     */
    @TableField(value = "out_url")
    private String outUrl;

    /**
     * 停留时间（单位：分）
     */
    @TableField(value = "parking_time")
    private Integer parkingTime;

    /**
     * 异常类型  0：超时停车  1：无入场记录  2：无出场纪录  3：大额订单
     */
    @TableField(value = "exception_type")
    private String exceptionType;

    /**
     * 车类型
     */
    @TableField(value = "car_type")
    private String carType;

    /**
     * 逻辑删除  0:已删除  1:未删除
     */
    @TableField(value = "deleted")
    private String deleted;

}
