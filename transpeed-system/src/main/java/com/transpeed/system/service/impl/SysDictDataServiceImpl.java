package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.system.entity.SysDictDataEntity;
import com.transpeed.system.mapper.SysDictDataMapper;
import com.transpeed.system.service.ISysDictDataService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author hanshuai
 * @title: SysDictDataServiceImpl
 * @description: 字典数据业务层
 * @date 2023/6/5 11:20
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictDataEntity> implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据字典类型查询字典值
     *
     * @param dictType 字典类型
     * @return 字典值
     */
    @Override
    public List<SysDictDataEntity> getDictDataByType(String dictType) {
        return this.dictDataMapper.selectList(Wrappers.<SysDictDataEntity>lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
                .orderByAsc(SysDictDataEntity::getOrderNum)
        );
    }

    /**
     * 根据字典类型集合查询字典值
     *
     * @param typeList 字典类型集合
     * @return 字典值
     */
    @Override
    public List<SysDictDataEntity> getDictDataByTypeList(List<String> typeList) {
        if (StringUtils.isEmpty(typeList)) {
            return Collections.emptyList();
        }
        return this.lambdaQuery()
                .in(SysDictDataEntity::getDictType, typeList)
                .orderByAsc(SysDictDataEntity::getOrderNum)
                .list();
    }

    /**
     * 添加字典值数据
     *
     * @param dictDataList 字典值集合
     * @param dictType     字典类型
     * @param dictTypeName 字典类型名称
     */
    @Override
    public void addDictData(List<SysDictDataEntity> dictDataList, String dictType, String dictTypeName) {
        dictDataList.forEach(dictData -> {
            dictData.setDictType(dictType);
            dictData.setCreateBy(SecurityUtils.getUsername());
            dictData.setRemark(dictTypeName);
            this.dictDataMapper.insert(dictData);
        });
    }

    /**
     * 修改字典值数据
     *
     * @param dictDataList 字典值集合
     * @param dictTypeName 字典类型名称
     * @param dictType     字典类型
     */
    @Override
    public void updateDictData(String dictType, String dictTypeName, List<SysDictDataEntity> dictDataList) {
        // 先删除
        this.dictDataMapper.delete(Wrappers.<SysDictDataEntity>lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
        );
        // 后添加
        this.addDictData(dictDataList, dictType, dictTypeName);
    }

    /**
     * 删除字典值数据
     *
     * @param dictType 字典类型
     */
    @Override
    public void delDictData(String dictType) {
        this.dictDataMapper.delete(Wrappers.<SysDictDataEntity>lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
        );
    }

}
