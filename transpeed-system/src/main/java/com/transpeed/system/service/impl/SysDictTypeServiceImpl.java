package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.constant.business.system.dict.DictConstants;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.constant.system.SystemStatus;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.system.common.dto.dict.SysDictTypeDto;
import com.transpeed.system.entity.SysDictDataEntity;
import com.transpeed.system.entity.SysDictTypeEntity;
import com.transpeed.system.mapper.SysDictTypeMapper;
import com.transpeed.system.service.ISysDictDataService;
import com.transpeed.system.service.ISysDictTypeService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hanshuai
 * @title: SysDictTypeServiceImpl
 * @description: 字典类型业务层
 * @date 2023/6/2 16:37
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictTypeEntity> implements ISysDictTypeService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    private void initDict() {
        this.initDictCache();
    }

    /**
     * 分页查询字典数据
     *
     * @param dictTypeDto 查询条件
     * @return 分页数据
     */
    @Override
    public DataGridView listForDictType(SysDictTypeDto dictTypeDto) {
        Page<SysDictTypeEntity> page = new Page<>(dictTypeDto.getPageNum(), dictTypeDto.getPageSize());
        this.dictTypeMapper.selectPage(page, Wrappers.<SysDictTypeEntity>lambdaQuery()
                // 字典名称
                .like(StringUtils.isNotEmpty(dictTypeDto.getDictTypeName()), SysDictTypeEntity::getDictTypeName, dictTypeDto.getDictTypeName())
                // 字典类型
                .like(StringUtils.isNotEmpty(dictTypeDto.getDictType()), SysDictTypeEntity::getDictType, dictTypeDto.getDictType())
                // 字典状态
                .eq(dictTypeDto.getDictTypeStatus() != null, SysDictTypeEntity::getDictTypeStatus, dictTypeDto.getDictTypeStatus())
                // ID正序
                .orderByAsc(SysDictTypeEntity::getDictTypeId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 查询字典数据信息
     *
     * @param dictTypeId 字典ID
     * @return 字典数据
     */
    @Override
    public SysDictTypeEntity getInfoDictType(Integer dictTypeId) {
        // 查询字典类型
        SysDictTypeEntity dictType = this.dictTypeMapper.selectById(dictTypeId);
        // 查询字典值
        dictType.setDictDataList(this.dictDataService.getDictDataByType(dictType.getDictType()));
        return dictType;
    }

    /**
     * 新增字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 新增结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addDictType(SysDictTypeDto dictTypeDto) {
        // 添加字典值
        this.dictDataService.addDictData(dictTypeDto.getDictDataList(), dictTypeDto.getDictType(), dictTypeDto.getDictTypeName());
        SysDictTypeEntity dictTypeEntity = new SysDictTypeEntity();
        BeanUtils.copyProperties(dictTypeDto, dictTypeEntity);
        dictTypeEntity.setCreateBy(SecurityUtils.getUsername());
        dictTypeEntity.setDictTypeStatus(SystemStatus.OK.getCode());
        // 添加字典类型
        return this.dictTypeMapper.insert(dictTypeEntity);
    }

    /**
     * 修改字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 修改结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateDictType(SysDictTypeDto dictTypeDto) {
        // 修改字典值
        this.dictDataService.updateDictData(dictTypeDto.getDictType(), dictTypeDto.getDictTypeName(), dictTypeDto.getDictDataList());
        SysDictTypeEntity dictTypeEntity = new SysDictTypeEntity();
        BeanUtils.copyProperties(dictTypeDto, dictTypeEntity);
        dictTypeEntity.setUpdateBy(SecurityUtils.getUsername());
        // 修改字典类型
        int dictCount = this.dictTypeMapper.updateById(dictTypeEntity);
        // 刷新当前修改字典的缓存
        List<SysDictDataEntity> dataList = this.dictDataService.getDictDataByType(dictTypeEntity.getDictType());
        if (StringUtils.isNotEmpty(dataList)) {
            this.redisCache.setCacheObject(DictConstants.DICT_TYPE_CACHE + dictTypeEntity.getDictType(), dataList);
        }
        return dictCount;
    }

    /**
     * 删除字典数据
     *
     * @param dictTypeId 字典ID
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delDictType(Integer dictTypeId) {
        SysDictTypeEntity dictTypeEntity = this.dictTypeMapper.selectById(dictTypeId);
        // 删除字典值
        this.dictDataService.delDictData(dictTypeEntity.getDictType());
        // 删除字典类型
        int dictCount = this.dictTypeMapper.deleteById(dictTypeId);
        // 删除字典缓存
        this.redisCache.deleteObject(DictConstants.DICT_TYPE_CACHE + dictTypeEntity.getDictType());
        return dictCount;
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    @Override
    public List<SysDictDataEntity> getDictData(String dictType) {
        List<SysDictDataEntity> dataList = redisCache.getCacheObject(DictConstants.DICT_TYPE_CACHE + dictType);
        if (StringUtils.isEmpty(dataList)) {
            dataList = this.dictDataService.getDictDataByType(dictType);
            if (StringUtils.isNotEmpty(dataList)) {
                this.redisCache.setCacheObject(DictConstants.DICT_TYPE_CACHE + dictType, dataList);
            }
        }
        return dataList;
    }

    /**
     * 校验字典类型名称是否存在
     *
     * @param dictTypeDto 字典类型
     * @return 是否存在
     */
    @Override
    public String checkDictNameUnique(SysDictTypeDto dictTypeDto) {
        Integer id = StringUtils.isNull(dictTypeDto.getDictTypeId()) ? -1 : dictTypeDto.getDictTypeId();
        SysDictTypeEntity dictType = this.lambdaQuery()
                .eq(SysDictTypeEntity::getDictTypeName, dictTypeDto.getDictTypeName())
                .last(SystemConstants.SQL_LIMIT)
                .one();
        if (StringUtils.isNotNull(dictType) && dictType.getDictTypeId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 校验字典类型是否存在
     *
     * @param dictTypeDto 字典类型
     * @return 是否存在
     */
    @Override
    public String checkDictTypeUnique(SysDictTypeDto dictTypeDto) {
        Integer id = StringUtils.isNull(dictTypeDto.getDictTypeId()) ? -1 : dictTypeDto.getDictTypeId();
        SysDictTypeEntity dictType = this.lambdaQuery()
                .eq(SysDictTypeEntity::getDictType, dictTypeDto.getDictType())
                .last(SystemConstants.SQL_LIMIT)
                .one();
        if (StringUtils.isNotNull(dictType) && dictType.getDictTypeId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 校验字典值集合内是否有重复标签
     *
     * @param dictDataList 字典值集合
     * @return 是否有重复
     */
    @Override
    public String checkDictDataLabelUnique(List<SysDictDataEntity> dictDataList) {
        // 将需要校验重复的属性收集到一个 Set 集合中
        Set<String> duplicateNames = dictDataList.stream()
                .map(SysDictDataEntity::getDictDataLabel)
                .collect(Collectors.toSet());
        // 如果 Set 集合的大小小于原始集合的大小，则表示存在重复的属性
        if (duplicateNames.size() < dictDataList.size()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 校验字典值集合内是否有重复值
     *
     * @param dictDataList 字典值集合
     * @return 是否有重复
     */
    @Override
    public String checkDictDataValueUnique(List<SysDictDataEntity> dictDataList) {
        // 将需要校验重复的属性收集到一个 Set 集合中
        Set<Integer> duplicateNames = dictDataList.stream()
                .map(SysDictDataEntity::getDictDataValue)
                .collect(Collectors.toSet());
        // 如果 Set 集合的大小小于原始集合的大小，则表示存在重复的属性
        if (duplicateNames.size() < dictDataList.size()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 校验集合内是否有空值
     *
     * @param dictDataList 字典数据集合
     * @return true：没有空值 false：有空值
     */
    @Override
    public boolean validateDictDataList(List<SysDictDataEntity> dictDataList) {
        for (SysDictDataEntity object : dictDataList) {
            try {
                // 获取对象的指定属性的值
                Object label = getProperty(object, "dictDataLabel");
                Object value = getProperty(object, "dictDataValue");

                // 判断属性值是否不为空
                if (StringUtils.isEmpty(String.valueOf(label)) || value == null) {
                    return false;
                }
            } catch (Exception e) {
                // 处理异常
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 获取指定属性
     */
    private static Object getProperty(Object object, String propertyName) throws Exception {
        // 使用反射获取对象的指定属性的值
        Class<?> clazz = object.getClass();
        java.lang.reflect.Field field = clazz.getDeclaredField(propertyName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 初始化字典数据进入缓存
     */
    private void initDictCache() {
        // 查询所有字典类型
        List<SysDictTypeEntity> typeList = this.lambdaQuery()
                .eq(SysDictTypeEntity::getDictTypeStatus, SystemConstants.SYSTEM_STATUS_NORMAL)
                .orderByAsc(SysDictTypeEntity::getDictTypeId)
                .list();
        // 根据类型查询字典数据
        Map<String, List<SysDictDataEntity>> dictDataMap = new HashMap<>();
        typeList.forEach(type -> dictDataMap.put(type.getDictType(), new ArrayList<>()));
        this.dictDataService.getDictDataByTypeList(typeList.stream().map(SysDictTypeEntity::getDictType).collect(Collectors.toList()))
                .forEach(data -> dictDataMap.get(data.getDictType()).add(data));
        // 放入缓存
        dictDataMap.forEach((key, value) -> this.redisCache.setCacheObject(DictConstants.DICT_TYPE_CACHE + key, value));
    }

}
