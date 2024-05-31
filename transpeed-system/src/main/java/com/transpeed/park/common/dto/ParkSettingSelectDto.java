package com.transpeed.park.common.dto;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ParkSettingSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/20 9:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkSettingSelectDto extends BaseDto {

    /**
     * 停车场编码
     */
    private String parkCode;

}
