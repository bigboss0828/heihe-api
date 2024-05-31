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
 * @title: InRecordEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:33
 * @Version 1.0
 */
@TableName("t_lane_in_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InRecordEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事务id
     */
    @TableField(value = "tx_id")
    private String txId;

    /**
     * 停车场编码
     */
    @TableField(value = "park_code")
    private String parkCode;

    /**
     * 车道编码
     */
    @TableField(value = "lane_code")
    private String laneCode;

    /**
     * 车道名称
     */
    @TableField(value = "lane_name")
    private String laneName;

    /**
     * 抓拍机识别车牌号
     */
    @TableField(value = "plate")
    private String plate;

    /**
     * 确认车牌号
     */
    @TableField(value = "amend_cn")
    private String amendCn;

    /**
     * 车牌颜色 （0：蓝色车牌  1：绿色车牌 ...）
     */
    @TableField(value = "plate_color")
    private String plateColor;

    @TableField(value = "ic_no")
    private String icNO;

    @TableField(value = "obu_id")
    private String obuId;

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
     * 触发开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = SystemConstants.TIME_ZONE)
    @TableField(value = "trigger_time")
    private Date triggerTime;

    /**
     * 上传的图片名
     */
    @TableField(value = "capture_name")
    private String captureName;

    /**
     * 图片url地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 放行方式
     */
    @TableField(value = "let_go_code")
    private String letGoCode;

    /**
     * 出场类型：值为三个一个是未出场，一个是车队模式，最后一个是已出场
     */
    @TableField(value = "out_type")
    private String outType;

    /**
     * 名单主键
     */
    @TableField(value = "list_id")
    private Integer listId;

    /**
     * 过车说明（备注）
     */
    @TableField(value = "reserv")
    private String reserv;

    /**
     * 车辆类型  （0：小车   1：中型车  2：大型车）
     */
    @TableField(value = "car_type")
    private String carType;

    /**
     * 白名单类型
     */
    @TableField(value = "white_type")
    private String whiteType;

    @TableField(exist = false)
    private String ownerName;

    @TableField("depart_name")
    private String departName;
}
