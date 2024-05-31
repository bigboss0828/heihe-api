package com.transpeed.record.common.vo;

import com.transpeed.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: OutRecordExportVo
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/11/30 14:12
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutRecordExportVo {

    @Excel(name = "车道名称")
    private String laneName;

    @Excel(name = "车牌")
    private String plate;

    @Excel(name = "obu号")
    private String obuId;

    @Excel(name = "ic卡号")
    private String icNO;

    @Excel(name = "姓名")
    private String ownerName;

    @Excel(name = "部门")
    private String departName;

    @Excel(name = "白名单类型")
    private String whiteType;

    @Excel(name = "时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date triggerTime;

}
