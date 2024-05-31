package com.transpeed.car.service;

import com.transpeed.car.common.dto.BlackSelectDto;
import com.transpeed.car.common.dto.WhiteSelectDto;
import com.transpeed.car.entity.BlackEntity;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.common.core.page.DataGridView;

/**
 * @title: BlackService
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:35
 * @Version 1.0
 */

public interface IBlackService {

    /**
     * 分页查询黑名单车辆
     * @param dto
     * @return
     */
    DataGridView listForBlack(BlackSelectDto dto);

    /**
     * 新增黑名单车辆
     * @param blackEntity
     * @return
     */
    int addBlackCar(BlackEntity blackEntity);

    int updateBlackCar(BlackEntity blackEntity);

    int delBlackCar(Integer[] ids);

    /**
     * 校验车牌重复
     * @param blackEntity
     * @return
     */
    String checkPlateUnique(BlackEntity blackEntity);

}
