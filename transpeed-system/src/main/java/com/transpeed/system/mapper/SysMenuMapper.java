package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.common.core.domain.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hanshuai
 * @title: SysMenuMapper
 * @description: 菜单数据层
 * @date 2023/6/1 14:38
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
}
