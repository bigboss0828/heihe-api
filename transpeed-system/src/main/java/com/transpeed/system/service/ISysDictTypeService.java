package com.transpeed.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.system.common.dto.dict.SysDictTypeDto;
import com.transpeed.system.entity.SysDictDataEntity;
import com.transpeed.system.entity.SysDictTypeEntity;

import java.util.List;

/**
 * @author hanshuai
 * @title: ISysDictTypeService
 * @description: 字典类型业务层
 * @date 2023/6/2 16:37
 */
public interface ISysDictTypeService extends IService<SysDictTypeEntity> {

    /**
     * 分页查询字典数据
     *
     * @param dictTypeDto 查询条件
     * @return 分页数据
     */
    DataGridView listForDictType(SysDictTypeDto dictTypeDto);

    /**
     * 查询字典数据信息
     *
     * @param dictTypeId 字典ID
     * @return 字典数据
     */
    SysDictTypeEntity getInfoDictType(Integer dictTypeId);

    /**
     * 新增字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 新增结果
     */
    int addDictType(SysDictTypeDto dictTypeDto);

    /**
     * 修改字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 修改结果
     */
    int updateDictType(SysDictTypeDto dictTypeDto);

    /**
     * 删除字典数据
     *
     * @param dictTypeId 字典ID
     * @return 删除结果
     */
    int delDictType(Integer dictTypeId);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    List<SysDictDataEntity> getDictData(String dictType);

    /**
     * 校验字典类型名称是否存在
     *
     * @param dictTypeDto 字典类型
     * @return 是否存在
     */
    String checkDictNameUnique(SysDictTypeDto dictTypeDto);

    /**
     * 校验字典类型是否存在
     *
     * @param dictTypeDto 字典类型
     * @return 是否存在
     */
    String checkDictTypeUnique(SysDictTypeDto dictTypeDto);

    /**
     * 校验字典值集合内是否有重复标签
     *
     * @param dictDataList 字典值集合
     * @return 是否有重复
     */
    String checkDictDataLabelUnique(List<SysDictDataEntity> dictDataList);

    /**
     * 校验字典值集合内是否有重复值
     *
     * @param dictDataList 字典值集合
     * @return 是否有重复
     */
    String checkDictDataValueUnique(List<SysDictDataEntity> dictDataList);

    /**
     * 校验集合内是否有空值
     *
     * @param dictDataList 字典数据集合
     * @return true：没有空值 false：有空值
     */
    boolean validateDictDataList(List<SysDictDataEntity> dictDataList);

}
