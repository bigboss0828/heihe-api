package com.transpeed.car.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: WhiteSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 19:07
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhiteSelectDto extends BaseDto {

    /**
     * 授权车道  （车道编码  多个之间用英文逗号隔开）
     */
    private String authLane;

    /**
     * obuid
     */
    private String obuId;

    /**
     * 车牌 （无颜色  例：苏A12345）
     */
    private String plate;

    /**
     * 车主名称
     */
    private String ownerName;

    /**
     * 车辆类型  （0：小车   1：中型车  2：大型车）
     */
    private String carType;

    /**
     * 白名单类型   （t_car_white_type的  type_key）
     */
    private String whiteType;

    /**
     * 是否过期： 0：未过期  1:已过期
     */
    private String overdue;

}
