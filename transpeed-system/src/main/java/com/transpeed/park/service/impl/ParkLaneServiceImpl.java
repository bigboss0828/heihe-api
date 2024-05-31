package com.transpeed.park.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.park.common.dto.ParkLaneSelectDto;
import com.transpeed.park.entity.ParkLaneEntity;
import com.transpeed.park.mapper.ParkLaneMapper;
import com.transpeed.park.service.IParkLaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @title: ParkLaneServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 11:05
 * @Version 1.0
 */
@Service
public class ParkLaneServiceImpl implements IParkLaneService {

    @Autowired
    private ParkLaneMapper parkLaneMapper;

    /**
     * 分页查询车道列表
     *
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForParkLane(ParkLaneSelectDto dto) {
        Page<ParkLaneEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.parkLaneMapper.selectPage(page, Wrappers.<ParkLaneEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getLaneName()), ParkLaneEntity::getLaneName, dto.getLaneName())
                .orderByDesc(ParkLaneEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 查询所有的车道
     *
     * @return
     */
    @Override
    public List<ParkLaneEntity> allListForParkLane() {
        return this.parkLaneMapper.selectList(Wrappers.<ParkLaneEntity>lambdaQuery());
    }

    /**
     * 新增车道
     *
     * @param parkLaneEntity
     * @return
     */
    @Override
    public int addParkLane(ParkLaneEntity parkLaneEntity) {
        return this.parkLaneMapper.insert(parkLaneEntity);
    }

    /**
     * 编辑车道
     *
     * @param parkLaneEntity
     * @return
     */
    @Override
    public int updateParkLane(ParkLaneEntity parkLaneEntity) {
        return this.parkLaneMapper.updateById(parkLaneEntity);
    }

    /**
     * 删除车道
     *
     * @param ids
     * @return
     */
    @Override
    public int delParkLane(Integer[] ids) {
        return this.parkLaneMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
