package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanshuai
 * @title: SysUserMapper
 * @description: 系统用户数据层
 * @date 2023/6/1 08:40
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
}
