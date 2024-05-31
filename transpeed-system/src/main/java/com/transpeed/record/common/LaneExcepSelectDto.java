package com.transpeed.record.common;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: LaneExcepSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaneExcepSelectDto extends BaseDto {

    /**
     * 车牌号码
     */
    private String plate;

}
