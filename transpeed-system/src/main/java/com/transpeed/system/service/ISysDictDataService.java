package com.transpeed.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.transpeed.system.entity.SysDictDataEntity;

import java.util.List;

/**
 * @author hanshuai
 * @title: ISysDictDataService
 * @description: 字典数据业务层
 * @date 2023/6/5 11:19
 */
public interface ISysDictDataService extends IService<SysDictDataEntity> {

    /**
     * 根据字典类型查询字典值
     *
     * @param dictType 字典类型
     * @return 字典值
     */
    List<SysDictDataEntity> getDictDataByType(String dictType);

    /**
     * 根据字典类型集合查询字典值
     *
     * @param typeList 字典类型集合
     * @return 字典值
     */
    List<SysDictDataEntity> getDictDataByTypeList(List<String> typeList);

    /**
     * 添加字典值数据
     *
     * @param dictDataList 字典值集合
     * @param dictType     字典类型
     * @param dictTypeName 字典类型名称
     */
    void addDictData(List<SysDictDataEntity> dictDataList, String dictType, String dictTypeName);

    /**
     * 修改字典值数据
     *
     * @param dictDataList 字典值集合
     * @param dictTypeName 字典类型名称
     * @param dictType     字典类型
     */
    void updateDictData(String dictType, String dictTypeName, List<SysDictDataEntity> dictDataList);

    /**
     * 删除字典值数据
     *
     * @param dictType 字典类型
     */
    void delDictData(String dictType);
}
