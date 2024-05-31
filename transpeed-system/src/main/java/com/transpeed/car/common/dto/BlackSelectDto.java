package com.transpeed.car.common.dto;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: BlackSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:34
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackSelectDto extends BaseDto {

    /*
     * 车牌
     */
    private String plate;

    /**
     * 车主名称
     */
    private String ownerName;

}
