package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.system.entity.SysDictDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author hanshuai
 * @title: SysDictDataMapper
 * @description: 字典值数据层
 * @date 2023/6/2 16:39
 */
@Repository
@Mapper
public interface SysDictDataMapper extends BaseMapper<SysDictDataEntity> {
}
