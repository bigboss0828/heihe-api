package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.system.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hanshuai
 * @title: SysRoleMenuMapper
 * @description: 角色菜单关联数据层
 * @date 2023/6/1 14:39
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

    /**
     * 批量添加角色与菜单绑定信息
     *
     * @param roleMenuList 角色菜单集合
     * @return 添加结果
     */
    int batchAdd(List<SysRoleMenuEntity> roleMenuList);

}
