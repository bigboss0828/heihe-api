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
 * @title: ParkEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 18:42
 * @Version 1.0
 */
@TableName("t_park")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车场编号（PV21001）
     */
    @TableField("park_code")
    private String parkCode;

    /**
     * 停车场密钥
     */
    @TableField("park_key")
    private String parkKey;

    /**
     * 停车场名称
     */
    @TableField("park_name")
    private String parkName;

    /**
     * 停车场地址
     */
    @TableField("park_address")
    private String parkAddress;

    /**
     * 是否虚拟停车场 0：是 1：否 （默认否）
     */
    @TableField("is_virtual")
    private String isVirtual;

    /**
     * 是否是场中场 0：是 1：否 （默认否）
     */
    @TableField("is_inner")
    private String isInner;

    /**
     * 是否收费 0：是 1：否 （默认否）
     */
    @TableField("is_charge")
    private String isCharge;

    /**
     * 收费方式 (英文逗号分隔 对应字典信息charge_way)
     */
    @TableField("charge_way")
    private String chargeWay;

    /**
     * 实际停车场CODE
     */
    @TableField("parent_park_code")
    private String parentParkCode;

    /**
     * 是否开启车位 0：开启 1：不开启
     */
    @TableField("car_space")
    private String carSpace;

    /**
     * 是否开启多车多位 0：开启 1：不开启
     */
    @TableField("is_multiple")
    private String isMultiple;

    /**
     * 是否开启优惠券 0：是 1：否 （默认否）
     */
    @TableField("is_coupon")
    private String isCoupon;

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
     * 总车位数
     */
    @TableField("total_space")
    private Integer totalSpace;

    /**
     * 临时车位数
     */
    @TableField("temp_space")
    private Integer tempSpace;

    /**
     * 固定车位数
     */
    @TableField("fixed_space")
    private Integer fixedSpace;

    /**
     * 入口数
     */
    @TableField("entrance_num")
    private Integer entranceNum;

    /**
     * 出口数
     */
    @TableField("exit_num")
    private Integer exitNum;

    /**
     * 初始化当前余位数
     */
    @TableField("free_space")
    private Integer freeSpace;

    /**
     * 联系人
     */
    @TableField("contact")
    private String contact;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 是否需要推送进出场记录，0需要，1不需要
     */
    @TableField("send_record")
    private String sendRecord;

    /**
     * 不收费停车场，临时车是否允许出场，0允许，1不允许
     */
    @TableField("no_charge_temp_out")
    private String noChargeTempOut;

    /**
     * 模式 0-正常模式 1-维护模式
     */
    @TableField("mode")
    private String mode;

}
