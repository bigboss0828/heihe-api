package com.transpeed.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.transpeed.common.core.domain.entity.SysMenuEntity;
import com.transpeed.system.common.dto.menu.SysMenuDto;
import com.transpeed.system.common.vo.menu.MenuSelectVo;
import com.transpeed.system.common.vo.menu.RouterVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hanshuai
 * @title: ISysMenuService
 * @description: 菜单业务层
 * @date 2023/6/1 14:41
 */
public interface ISysMenuService extends IService<SysMenuEntity> {

    /**
     * 查询菜单表格数据
     *
     * @param menuDto 查询条件
     * @return 表格数据
     */
    List<RouterVo> listForMenu(SysMenuDto menuDto);

    /**
     * 查询菜单数据信息
     *
     * @param menuId 菜单ID
     * @return 菜单数据
     */
    SysMenuEntity getInfoMenu(Integer menuId);

    /**
     * 添加菜单信息
     *
     * @param menuDto 菜单实体
     * @return 添加结果
     */
    int addMenu(SysMenuDto menuDto);

    /**
     * 修改菜单信息
     *
     * @param menuDto 菜单实体
     * @return 修改结果
     */
    int updateMenu(SysMenuDto menuDto);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 删除结果
     */
    int delMenu(Integer menuId);

    /**
     * 校验菜单名称是否存在
     *
     * @param menuDto 字典类型
     * @return 是否存在
     */
    String checkMenuTitleUnique(SysMenuDto menuDto);

    /**
     * 查询所有菜单 树形结构（选择父级、绑定角色）
     *
     * @return 树形结构菜单
     */
    List<MenuSelectVo> getSelectMenu();

    /**
     * 获取用户菜单信息
     *
     * @param userId 用户ID
     * @return 菜单信息
     */
    Map<String, Object> getUserInfo(Integer userId);

    /**
     * 根据菜单ID 查询按钮权限
     *
     * @param menuIdList 菜单ID集合
     * @param isAdmin    是否为超级管理员（超级管理员查询全部）
     * @return 按钮权限
     */
    Set<String> getMenusByIdList(List<Integer> menuIdList, boolean isAdmin);

}
