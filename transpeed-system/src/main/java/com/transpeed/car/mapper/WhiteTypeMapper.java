package com.transpeed.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.car.entity.WhiteTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title: WhiteTypeMapper
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 12:11
 * @Version 1.0
 */
@Mapper
public interface WhiteTypeMapper extends BaseMapper<WhiteTypeEntity> {

    int findMaxValue();

}
