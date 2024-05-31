package com.transpeed.park.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ParkLaneSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/18 11:21
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkLaneSelectDto extends BaseDto {

    /**
     * 车道名称
     */
    private String laneName;

}
