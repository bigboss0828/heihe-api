package com.transpeed.park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.park.entity.ParkEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @title: ParkMapper
 * @Author zhangwenxiang
 * @Date: 2023/9/13 18:51
 * @Version 1.0
 */

@Mapper
public interface ParkMapper extends BaseMapper<ParkEntity> {
}
