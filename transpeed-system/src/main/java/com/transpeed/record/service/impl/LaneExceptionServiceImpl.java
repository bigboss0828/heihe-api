package com.transpeed.record.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.record.common.LaneExcepSelectDto;
import com.transpeed.record.entity.LaneExceptionEntity;
import com.transpeed.record.mapper.LaneExceptionMapper;
import com.transpeed.record.service.ILaneExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: LaneExceptionServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:01
 * @Version 1.0
 */
@Service
public class LaneExceptionServiceImpl implements ILaneExceptionService {

    @Autowired
    private LaneExceptionMapper laneExceptionMapper;

    @Override
    public DataGridView listForLaneException(LaneExcepSelectDto dto) {
        Page<LaneExceptionEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.laneExceptionMapper.selectPage(page, Wrappers.<LaneExceptionEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getPlate()), LaneExceptionEntity::getPlate, dto.getPlate())
                .eq(LaneExceptionEntity::getDeleted, "1")
                .orderByDesc(LaneExceptionEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }
}
