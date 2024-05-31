package com.transpeed.car.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.car.common.dto.BlackSelectDto;
import com.transpeed.car.entity.BlackEntity;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.car.mapper.BlackMapper;
import com.transpeed.car.service.IBlackService;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @title: BlackServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:36
 * @Version 1.0
 */
@Service
public class BlackServiceImpl implements IBlackService {

    @Autowired
    private BlackMapper blackMapper;

    /**
     * 分页查询黑名单
     *
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForBlack(BlackSelectDto dto) {
        Page<BlackEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.blackMapper.selectPage(page, Wrappers.<BlackEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getPlate()), BlackEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getOwnerName()), BlackEntity::getOwnerName, dto.getOwnerName())
                .ge(dto.getBeginTime() != null, BlackEntity::getStartTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, BlackEntity::getEndTime, dto.getEndTime())
                .orderByDesc(BlackEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 新增黑名单车辆
     *
     * @param blackEntity
     * @return
     */
    @Override
    public int addBlackCar(BlackEntity blackEntity) {
        return this.blackMapper.insert(blackEntity);
    }

    /**
     * 更新黑名单车辆
     * @param blackEntity
     * @return
     */
    @Override
    public int updateBlackCar(BlackEntity blackEntity) {
        return this.blackMapper.updateById(blackEntity);
    }

    @Override
    public int delBlackCar(Integer[] ids) {
        return this.blackMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 校验车牌重复
     * @param blackEntity
     * @return
     */
    @Override
    public String checkPlateUnique(BlackEntity blackEntity) {
        Integer id = StringUtils.isNull(blackEntity.getId()) ? -1 : blackEntity.getId();
        BlackEntity blackCar = this.blackMapper.selectOne(Wrappers.<BlackEntity>lambdaQuery()
                .eq(BlackEntity::getPlate, blackEntity.getPlate())
                .or()
                .eq(BlackEntity::getObuId, blackEntity.getObuId())
                .last(SystemConstants.SQL_LIMIT)
        );
        if (StringUtils.isNotNull(blackCar) && blackCar.getId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }
}
