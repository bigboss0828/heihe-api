package com.transpeed.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.personnel.entity.PersonnelLevelEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title: PersonnelLevelMapper
 * @Author zhangwenxiang
 * @Date: 2023/9/14 10:33
 * @Version 1.0
 */

@Mapper
public interface PersonnelLevelMapper extends BaseMapper<PersonnelLevelEntity> {

    String findMaxValue();

}
