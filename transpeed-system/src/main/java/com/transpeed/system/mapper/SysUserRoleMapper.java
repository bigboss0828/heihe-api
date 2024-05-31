package com.transpeed.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.transpeed.system.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hanshuai
 * @title: SysUserRoleMapper
 * @description: 用户角色数据层
 * @date 2023/6/1 14:39
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

    /**
     * 批量添加用户与角色绑定信息
     *
     * @param userRoleList 用户角色集合
     * @return 添加结果
     */
    int batchAdd(List<SysUserRoleEntity> userRoleList);

}
