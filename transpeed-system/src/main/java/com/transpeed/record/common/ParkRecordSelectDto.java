package com.transpeed.record.common;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ParkRecordSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:53
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkRecordSelectDto extends BaseDto {

    /**
     * 车牌号码
     */
    private String plate;

    /**
     * 白名单类型
     */
    private String whiteType;

    private String personLevel;

    private String departName;

    private String company;

}
