package com.transpeed.park.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.park.common.dto.ParkSelectDto;
import com.transpeed.park.entity.ParkEntity;
import com.transpeed.park.mapper.ParkMapper;
import com.transpeed.park.service.IParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: ParkServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 18:52
 * @Version 1.0
 */
@Service
public class ParkServiceImpl implements IParkService {

    @Autowired
    private ParkMapper parkMapper;

    /**
     * 分页查询停车场
     *
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForPark(ParkSelectDto dto) {
        Page<ParkEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.parkMapper.selectPage(page, Wrappers.<ParkEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getParkName()), ParkEntity::getParkName, dto.getParkName())
                .orderByDesc(ParkEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 编辑停车场
     *
     * @param parkEntity
     * @return
     */
    @Override
    public int updatePark(ParkEntity parkEntity) {
        return this.parkMapper.updateById(parkEntity);
    }
}
