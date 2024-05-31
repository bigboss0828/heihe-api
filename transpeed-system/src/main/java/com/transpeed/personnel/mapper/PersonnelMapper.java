package com.transpeed.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.personnel.entity.PersonnelEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title: PersonnelMapper
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:22
 * @Version 1.0
 */

@Mapper
public interface PersonnelMapper extends BaseMapper<PersonnelEntity> {
}
