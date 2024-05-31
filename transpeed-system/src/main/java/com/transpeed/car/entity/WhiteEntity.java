package com.transpeed.car.entity;

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
 * @title: WhiteEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 18:59
 * @Version 1.0
 */
@TableName("t_car_white")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhiteEntity extends BaseEntity {

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
     * 授权车道  （车道编码  多个之间用英文逗号隔开）
     */
    @TableField("auth_lane")
    private String authLane;

    /**
     * obuid
     */
    @Excel(name = "obu号")
    @TableField("obu_id")
    private String obuId;

    /**
     * 车牌 （无颜色  例：苏A12345）
     */
    @Excel(name = "车牌号")
    @TableField("plate")
    private String plate;

    /**
     * 车牌颜色 （0：蓝色车牌  1：绿色车牌  2：黄色车牌  3：黑色车牌  4：白色车牌  5：红色车牌  6：渐变绿色  7：黄绿双拼  8：蓝白渐变  -1：未确定）
     */
    @TableField("plate_color")
    private String plateColor;

    /**
     * 车主名称
     */
    @Excel(name = "车主名称")
    @TableField("owner_name")
    private String ownerName;

    /**
     * 车主电话
     */
    @Excel(name = "车主电话")
    @TableField("owner_phone")
    private String ownerPhone;

    /**
     * 车辆类型  （0：小车   1：中型车  2：大型车）
     */
    @TableField("car_type")
    private String carType;

    /**
     * 白名单类型   （t_car_white_type的  type_key）
     */
    @TableField("white_type")
    private String whiteType;

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
     * 是否逻辑删除： 0，未逻辑删除，1代表逻辑删除
     */
    @TableField("logic_del")
    private String logicDel;

    /**
     * 是否过期： 0：未过期  1:已过期
     */
    @TableField("overdue")
    private String overdue;

    /**
     * 人员等级
     */
//    @TableField("person_level")
//    private String personLevel;

}
