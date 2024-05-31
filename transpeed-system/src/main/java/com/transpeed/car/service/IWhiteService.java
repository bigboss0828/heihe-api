package com.transpeed.car.service;

import com.transpeed.car.common.dto.WhiteSelectDto;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.common.core.page.DataGridView;

import java.util.List;

/**
 * @title: IWhiteService
 * @Author zhangwenxiang
 * @Date: 2023/9/14 19:05
 * @Version 1.0
 */

public interface IWhiteService {

    /**
     * 分页查询黑名单车辆
     *
     * @param whiteSelectDto
     * @return
     */
    DataGridView listForWhite(WhiteSelectDto whiteSelectDto);

    /**
     * 新增白名单车
     *
     * @param whiteEntity
     * @return
     */
    int addWhiteCar(WhiteEntity whiteEntity);

    /**
     * 修改白名单车辆
     *
     * @param whiteEntity
     * @return
     */
    int updateWhiteCar(WhiteEntity whiteEntity);

    int delWhiteCar(Integer[] ids);

    /**
     * 校验车牌重复
     *
     * @param whiteEntity
     * @return
     */
    String checkPlateUnique(WhiteEntity whiteEntity);

    String importWhite(List<WhiteEntity> whites, String whiteType, String authLane);

}
