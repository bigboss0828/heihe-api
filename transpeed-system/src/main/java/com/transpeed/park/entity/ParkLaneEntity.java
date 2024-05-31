package com.transpeed.park.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ParkLaneEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 11:01
 * @Version 1.0
 */
@TableName("t_park_lane")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkLaneEntity extends BaseEntity {

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
     * 车道编码
     */
    @TableField("lane_code")
    private String laneCode;

    /**
     * 车道名称
     */
    @TableField("lane_name")
    private String laneName;

    /**
     * 经度（百度）
     */
    @TableField("log")
    private Double log;

    /**
     * 纬度（百度）
     */
    @TableField("lat")
    private Double lat;

    /**
     * 是否为场内车道  0：是  1：否
     */
    @TableField("is_inner")
    private String isInner;

    /**
     * 车道类型  0：进场  1：出场
     */
    @TableField("lane_type")
    private String laneType;

    /**
     * 车控器IP
     */
    @TableField("lane_ip")
    private String laneIp;

    /**
     * 车控器IP（备份）
     */
    @TableField("lane_ip_backups")
    private String laneIpBackups;

    /**
     * 支付设备号
     */
    @TableField("pay_device_no")
    private String payDeviceNo;

    /**
     * 车辆入场LED显示内容
     */
    @TableField("led_text_in")
    private String ledTextIn;

    /**
     * 车辆出场LED显示内容
     */
    @TableField("led_text_out")
    private String ledTextOut;

    /**
     * 车辆缴费LED显示内容
     */
    @TableField("led_text_pay")
    private String ledTextPay;

    /**
     * 车辆禁止LED显示内容
     */
    @TableField("led_text_ban")
    private String ledTextBan;

    /**
     * 临时车是否让进  0让进1不让进
     */
    @TableField("is_temp_car_in")
    private String isTempCarIn;

}
