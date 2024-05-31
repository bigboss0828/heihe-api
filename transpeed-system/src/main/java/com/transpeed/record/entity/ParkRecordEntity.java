package com.transpeed.record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.constant.system.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: ParkRecordEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:52
 * @Version 1.0
 */
@TableName("t_lane_park_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkRecordEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 事务id
     */
    @TableField("tx_id")
    private String txId;

    /**
     * 停车场编码
     */
    @TableField("park_code")
    private String parkCode;

    /**
     * 停车场名称
     */
    @TableField("park_name")
    private String parkName;

    /**
     * 车牌号码
     */
    @TableField("plate")
    private String plate;

    /**
     * 车牌颜色
     */
    @TableField("plate_color")
    private char plateColor;

    /**
     * 进场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("in_time")
    private Date inTime;

    /**
     * 出场时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField("out_time")
    private Date outTime;

    /**
     * 进场url
     */
    @TableField("in_url")
    private String inUrl;

    /**
     * 出场url
     */
    @TableField("out_url")
    private String outUrl;

    /**
     * 凭证号
     */
    @TableField("voucher")
    private String voucher;

    /**
     * 凭证类型
     */
    @TableField("voucher_type")
    private String voucherType;

    /**
     * 白名单类型
     */
    @TableField("white_type")
    private String whiteType;

    /**
     * 进场车道code
     */
    @TableField("in_lane_code")
    private String inLaneCode;

    /**
     * 入口车道名称
     */
    @TableField("in_lane_name")
    private String inLaneName;

    /**
     * 出口车道code
     */
    @TableField("out_lane_code")
    private String outLaneCode;

    /**
     * 出口车道名称
     */
    @TableField("out_lane_name")
    private String outLaneName;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 人员等级
     */
    @TableField("person_level")
    private String personLevel;

    /**
     * 停车时长
     */
    @TableField("park_time")
    private Integer parkTime;

    @TableField(exist = false)
    private String ownerName;

    @TableField("depart_name")
    private String departName;

    @TableField(exist = false)
    private Integer totalTime;
}
