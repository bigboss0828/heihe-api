package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.system.entity.SysLoginInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanshuai
 * @title: SysLoginInfoMapper
 * @description: 登录日志数据层
 * @date 2023/6/1 10:00
 */
@Mapper
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfoEntity> {
}
