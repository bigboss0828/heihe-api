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
 * @title: ParkSettingEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/20 8:55
 * @Version 1.0
 */
@TableName("t_park_setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkSettingEntity extends BaseEntity {

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
     * 临时车是否允许进场
     */
    @TableField(value = "is_temp_car_in")
    private char isTempCarIn;

    /**
     * 临时车允许进场开始时间 (HH:mm:ss)
     */
    @TableField(value = "temp_car_in_start_time")
    private String tempCarInStartTime;

    /**
     * 临时车允许进场结束时间 (HH:mm:ss)
     */
    @TableField(value = "temp_car_in_end_time")
    private String tempCarInEndTime;

    /**
     * 是否允许军警车进场
     */
    @TableField(value = "is_patrol_in")
    private char isPatrolIn;

    /**
     * 军车警车是否免费 (不免费按临时车处理)
     */
    @TableField(value = "is_free_patrol")
    private char isFreePatrol;

    /**
     * 车位满时临时车是否能进
     */
    @TableField(value = "space_full_temp_car_is_in")
    private char spaceFullTempCarIsIn;

    /**
     * 车位满时试验车辆是否能进
     */
    @TableField(value = "space_full_test_car_is_in")
    private char spaceFullTestCarIsIn;

    /**
     * 车位满时公务车是否可进
     */
    @TableField(value = "space_full_official_car_is_in")
    private char spaceFullOfficialCarIsIn;

    /**
     * 车位满时维修车是否可进
     */
    @TableField(value = "space_full_repair_car_is_in")
    private char spaceFullRepairCarIsIn;

    /**
     * 车位满时Demo是否可进
     */
    @TableField(value = "space_full_demo_car_is_in")
    private char spaceFullDemoCarIsIn;

    /**
     * 临时车是否占车位
     */
    @TableField(value = "is_temp_car_space")
    private char isTempCarSpace;

    /**
     * 试验车是否占车位
     */
    @TableField(value = "is_test_car_space")
    private char isTestCarSpace;

    /**
     * 公务车是否占车位
     */
    @TableField(value = "is_official_car_space")
    private char isOfficialCarSpace;

    /**
     * 维修车是否占车位
     */
    @TableField(value = "is_repair_car_sapce")
    private char isRepairCarSpace;

    /**
     * Demo是否占车位
     */
    @TableField(value = "is_demo_car_sapce")
    private char isDemoCarSpace;

    /**
     * 是否模糊匹配白名单进场
     */
    @TableField(value = "is_fuzzy_matching_in")
    private char isFuzzyMatchingIn;

    /**
     * 是否模糊匹配白名单出场
     */
    @TableField(value = "is_fuzzy_matching_out")
    private char isFuzzyMatchingOut;

    /**
     * 无入场记录是否放行
     */
    @TableField(value = "is_no_in_record_pass")
    private char isNoInRecordPass;

    /**
     * 无入场记录固定收费 单位:分
     */
    @TableField(value = "no_in_record_charge")
    private int noInRecordCharge;

    /**
     * 支付后多久过期 单位:分钟
     */
    @TableField(value = "pay_expire_time")
    private int payExpireTime;

    /**
     * 月租(单位：分)
     */
    @TableField(value = "monthly_rent")
    private int monthlyRent;

    /**
     * 本地车前缀 (例：苏E,苏U   英文逗号分隔)
     */
    @TableField(value = "plate_prefix")
    private String platePrefix;

    /**
     * 是否提示有效期剩余天数
     */
    @TableField(value = "is_validity_period")
    private char isValidityPeriod;

    /**
     * 过期提示
     */
    @TableField(value = "overdue_text")
    private String overdueText;

    /**
     * 过期提示时间(提前多少天进行LED回显过期提示)
     */
    @TableField(value = "overdue_time")
    private int overdueTime;

}
