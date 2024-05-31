package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.common.core.domain.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanshuai
 * @title: SysRoleMapper
 * @description: 角色数据层
 * @date 2023/6/1 14:38
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
}
