package com.transpeed.park.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.park.common.dto.ParkSelectDto;
import com.transpeed.park.entity.ParkEntity;

/**
 * @title: IParkService
 * @Author zhangwenxiang
 * @Date: 2023/9/13 18:52
 * @Version 1.0
 */

public interface IParkService {

    /**
     * 分页查询停车场
     * @param dto
     * @return
     */
    DataGridView listForPark(ParkSelectDto dto);

    /**
     * 编辑停车场
     * @param parkEntity
     * @return
     */
    int updatePark(ParkEntity parkEntity);

}
