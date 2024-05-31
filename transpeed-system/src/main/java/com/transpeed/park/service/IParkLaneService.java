package com.transpeed.park.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.park.common.dto.ParkLaneSelectDto;
import com.transpeed.park.entity.ParkLaneEntity;

import java.util.List;

/**
 * @title: IParkLaneService
 * @Author zhangwenxiang
 * @Date: 2023/9/15 11:04
 * @Version 1.0
 */


public interface IParkLaneService {

    /**
     * 分页查询车道列表
     * @param dto
     * @return
     */
    DataGridView listForParkLane(ParkLaneSelectDto dto);


    /**
     * 查询所有的车道
     * @return
     */
    List<ParkLaneEntity> allListForParkLane();

    /**
     * 新增车道
     * @param parkLaneEntity
     * @return
     */
    int addParkLane(ParkLaneEntity parkLaneEntity);

    /**
     * 编辑车道
     * @param parkLaneEntity
     * @return
     */
    int updateParkLane(ParkLaneEntity parkLaneEntity);

    /**
     * 删除车道
     *
     * @param ids
     * @return
     */
    int delParkLane(Integer[] ids);

}
