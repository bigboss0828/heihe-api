package com.transpeed.record.common;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: OutRecordSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:30
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutRecordSelectDto extends BaseDto {

    /**
     * 车道名称
     */
    private String laneName;

    /**
     * 抓拍机识别车牌号
     */
    private String plate;

    /**
     * 车辆类型  （0：小车   1：中型车  2：大型车）
     */
    private String carType;

    /**
     * 白名单类型
     */
    private String whiteType;

    private String icNO;

    private String obuId;
}
