package com.transpeed.record.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.transpeed.common.annotation.Excel;
import com.transpeed.common.constant.system.SystemConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: ParkRecordExportVo
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/11/30 9:50
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkRecordExportVo {

    /**
     * 车牌号码
     */
    @Excel(name = "车牌")
    private String plate;

    @Excel(name = "obu")
    private String obu;

    /**
     * 入口车道名称
     */
    @Excel(name = "入场车道")
    private String inLaneName;

    /**
     * 进场时间
     */
    @Excel(name = "入场时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;

    /**
     * 出口车道名称
     */
    @Excel(name = "出场车道")
    private String outLaneName;

    /**
     * 出场时间
     */
    @Excel(name = "出场时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;


    /**
     * 白名单类型
     */
    @Excel(name = "白名单类型")
    private String whiteType;

    /**
     * 人员等级
     */
    @Excel(name = "人员等级")
    private String personLevel;

    @Excel(name = "姓名")
    private String ownerName;

    @Excel(name = "公司")
    private String company;

    @Excel(name = "部门")
    private String departName;

    @Excel(name = "时长")
    private String parkTime;

}
