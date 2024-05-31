package com.transpeed.car.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.car.common.dto.WhiteTypeSelectDto;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.car.entity.WhiteTypeEntity;
import com.transpeed.car.mapper.WhiteTypeMapper;
import com.transpeed.car.service.IWhiteTypeService;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @title: WhiteTypeServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 12:12
 * @Version 1.0
 */
@Service
public class WhiteTypeServiceImpl implements IWhiteTypeService {

    @Autowired
    private WhiteTypeMapper whiteTypeMapper;

    /**
     * 分页查询白名单类型
     *
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForWhiteType(WhiteTypeSelectDto dto) {
        Page<WhiteTypeEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.whiteTypeMapper.selectPage(page, Wrappers.<WhiteTypeEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getTypeName()), WhiteTypeEntity::getTypeName, dto.getTypeName())
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 查询所有白名单类型
     *
     * @return
     */
    @Override
    public List<WhiteTypeEntity> listForAllWhiteType() {
        return this.whiteTypeMapper.selectList(Wrappers.<WhiteTypeEntity>lambdaQuery());
    }

    /**
     * 新增白名单类型
     *
     * @param whiteTypeEntity
     * @return
     */
    @Override
    public int addWhiteType(WhiteTypeEntity whiteTypeEntity) {
        return this.whiteTypeMapper.insert(whiteTypeEntity);
    }

    @Override
    public int getMaxWhiteKey() {
        return this.whiteTypeMapper.findMaxValue();
    }

    /**
     * 更新白名单
     * @param whiteTypeEntity
     * @return
     */
    @Override
    public int updateWhiteType(WhiteTypeEntity whiteTypeEntity) {
        return this.whiteTypeMapper.updateById(whiteTypeEntity);
    }

    /**
     * 删除白名单类型
     * @param ids
     * @return
     */
    @Override
    public int delWhiteType(Integer[] ids) {
        return this.whiteTypeMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
