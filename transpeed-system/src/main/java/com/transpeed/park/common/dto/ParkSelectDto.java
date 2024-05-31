package com.transpeed.park.common.dto;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ParkSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 18:55
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkSelectDto extends BaseDto {

    /**
     * 停车场名称
     */
    private String parkName;

}
