package com.transpeed.car.common.dto;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: WhiteTypeSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhiteTypeSelectDto extends BaseDto {

    private String typeName;

}
