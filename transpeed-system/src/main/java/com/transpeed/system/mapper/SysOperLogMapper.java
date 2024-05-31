package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.system.entity.SysOperLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanshuai
 * @title: SysOperLogMapper
 * @description: 操作日志数据层
 * @date 2023/6/19 10:30
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLogEntity> {
}
