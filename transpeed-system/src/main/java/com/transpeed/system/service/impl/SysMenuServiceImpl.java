package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.constant.business.system.menu.MenuType;
import com.transpeed.common.constant.business.system.role.RoleConstants;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.constant.system.SystemStatus;
import com.transpeed.common.core.domain.entity.SysMenuEntity;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.system.common.dto.menu.SysMenuDto;
import com.transpeed.system.common.vo.menu.MenuSelectVo;
import com.transpeed.system.common.vo.menu.Meta;
import com.transpeed.system.common.vo.menu.RouterVo;
import com.transpeed.system.entity.SysRoleMenuEntity;
import com.transpeed.system.mapper.SysMenuMapper;
import com.transpeed.system.mapper.SysRoleMenuMapper;
import com.transpeed.system.service.ISysMenuService;
import com.transpeed.system.service.ISysUserService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.common.utils.business.SysUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hanshuai
 * @title: SysMenuServiceImpl
 * @description: 菜单业务层
 * @date 2023/6/1 14:42
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询菜单表格数据
     *
     * @param menuDto 查询条件
     * @return 表格数据
     */
    @Override
    public List<RouterVo> listForMenu(SysMenuDto menuDto) {
        List<SysMenuEntity> menuCataList = this.menuMapper.selectList(Wrappers.<SysMenuEntity>lambdaQuery()
                // 过滤掉停用状态的菜单
                .ne(SysMenuEntity::getMenuStatus, SystemStatus.DISABLE.getCode())
                .orderByAsc(SysMenuEntity::getOrderNum)
        );
        return this.buildRouter(menuCataList, MenuType.CATA.getCode());
    }

    /**
     * 查询菜单数据信息
     *
     * @param menuId 菜单ID
     * @return 菜单数据
     */
    @Override
    public SysMenuEntity getInfoMenu(Integer menuId) {
        return this.menuMapper.selectById(menuId);
    }

    /**
     * 添加菜单信息
     *
     * @param menuDto 菜单实体
     * @return 添加结果
     */
    @Override
    public int addMenu(SysMenuDto menuDto) {
        SysMenuEntity menuEntity = new SysMenuEntity();
        BeanUtils.copyProperties(menuDto, menuEntity);
        // 创建人
        menuEntity.setCreateBy(SecurityUtils.getUsername());
        // 如果添加的菜单类型为按钮  则按钮的name与父级是相同的 为了符合前端校验规则
        if (MenuType.BTN.getCode().equals(menuDto.getMenuType())) {
            menuEntity.setMenuName(this.menuMapper.selectById(menuDto.getParentId()).getMenuName());
        }
        // 菜单状态默认正常
        menuEntity.setMenuStatus(SystemStatus.OK.getCode());
        // 清空所有用户缓存的菜单数据
        this.clearUserMenuCache();
        return this.menuMapper.insert(menuEntity);
    }

    /**
     * 修改菜单信息
     *
     * @param menuDto 菜单实体
     * @return 修改结果
     */
    @Override
    public int updateMenu(SysMenuDto menuDto) {
        SysMenuEntity menuEntity = new SysMenuEntity();
        BeanUtils.copyProperties(menuDto, menuEntity);
        // 修改人
        menuEntity.setUpdateBy(SecurityUtils.getUsername());
        // 清空所有用户缓存的菜单数据
        this.clearUserMenuCache();
        return this.menuMapper.updateById(menuEntity);
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 删除结果
     */
    @Override
    public int delMenu(Integer menuId) {
        List<SysRoleMenuEntity> roleMenus = this.roleMenuMapper.selectList(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                .eq(SysRoleMenuEntity::getMenuId, menuId)
        );
        if (roleMenus.size() > 0) {
            // 如果当前菜单已经授权角色 不允许删除
            throw new ServiceException("当前菜单已经授权角色，不允许删除！");
        }
        // 清空所有用户缓存的菜单数据
        this.clearUserMenuCache();
        return this.menuMapper.deleteById(menuId);
    }

    /**
     * 校验菜单名称是否存在
     *
     * @param menuDto 字典类型
     * @return 是否存在
     */
    @Override
    public String checkMenuTitleUnique(SysMenuDto menuDto) {
        Integer id = StringUtils.isNull(menuDto.getMenuId()) ? -1 : menuDto.getMenuId();
        SysMenuEntity menuEntity = this.lambdaQuery()
                .eq(SysMenuEntity::getTitle, menuDto.getTitle())
                .last(SystemConstants.SQL_LIMIT)
                .one();
        if (StringUtils.isNotNull(menuEntity) && menuEntity.getMenuId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 查询所有菜单 树形结构（选择父级、绑定角色）
     *
     * @return 树形结构菜单
     */
    @Override
    public List<MenuSelectVo> getSelectMenu() {
        List<SysMenuEntity> menuCataList = this.menuMapper.selectList(Wrappers.<SysMenuEntity>lambdaQuery()
                // 过滤掉停用状态的菜单
                .ne(SysMenuEntity::getMenuStatus, SystemStatus.DISABLE.getCode())
                .orderByAsc(SysMenuEntity::getOrderNum)
        );
        return this.buildSelect(menuCataList, MenuType.CATA.getCode());
    }

    /**
     * 获取用户菜单信息
     *
     * @param userId 用户ID
     * @return 菜单信息
     */
    @Override
    public Map<String, Object> getUserInfo(Integer userId) {
        SysUserEntity user = this.userService.getInfoUser(userId);
        List<SysMenuEntity> menuList;
        if (SysUtils.isAdmin(user.getUserType())) {
            menuList = this.menuMapper.selectList(Wrappers.<SysMenuEntity>lambdaQuery());
        } else {
            List<Integer> roleIdList = user.getRoleIdList();
            // 用户角色为空抛出异常
            if (StringUtils.isEmpty(roleIdList)) {
                throw new ServiceException(RoleConstants.USER_NOT_HAS_ROLE);
            }
            // 通过角色查询拥有权限的菜单ID
            List<SysRoleMenuEntity> roleMenus = this.roleMenuMapper.selectList(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                    .in(SysRoleMenuEntity::getRoleId, roleIdList)
            );
            // 当前用户角色暂无菜单权限
            if (StringUtils.isEmpty(roleMenus)) {
                throw new ServiceException(RoleConstants.ROLE_NOT_HAS_MENU);
            }
            // 查询出所有拥有权限的菜单和按钮
            menuList = this.menuMapper.selectBatchIds(
                    roleMenus.stream().map(SysRoleMenuEntity::getMenuId).collect(Collectors.toList())
            );
        }
        // 构造菜单路由、按钮权限
        List<SysMenuEntity> router = new ArrayList<>();
        Map<String, List> btnPer = new HashMap<>();
        menuList.forEach(menu -> {
            if (MenuType.BTN.getCode().equals(menu.getMenuType())) {
                String menuName = menu.getMenuName();
                String perms = menu.getPerms();
                btnPer.computeIfAbsent(menuName, k -> new ArrayList<>()).add(perms);
            } else {
                router.add(menu);
            }
        });
        Map<String, Object> result = new HashMap<>();
        result.put("router", this.buildRouter(router, MenuType.CATA.getCode()));
        result.put("btnPerms", btnPer);
//        Map<String, Object> loginUser = new HashMap<>();
//        loginUser.put("username", SecurityUtils.getLoginUser().getUsername());
//        loginUser.put("userType", SecurityUtils.getLoginUser().getUserType());
//        loginUser.put("userId", SecurityUtils.getLoginUser().getUserId());
//        result.put("loginUser", loginUser);
        result.put("loginUser", SecurityUtils.getLoginUser());
        return result;
    }

    /**
     * 根据菜单ID 查询按钮权限
     *
     * @param menuIdList 菜单ID集合
     * @param isAdmin    是否为超级管理员（超级管理员查询全部）
     * @return 按钮权限
     */
    @Override
    public Set<String> getMenusByIdList(List<Integer> menuIdList, boolean isAdmin) {
        return this.menuMapper.selectList(Wrappers.<SysMenuEntity>lambdaQuery()
                        .in(!isAdmin, SysMenuEntity::getMenuId, menuIdList)
                ).stream()
                .map(SysMenuEntity::getPerms)
                .collect(Collectors.toSet());
    }

    /**
     * 递归构造菜单信息
     *
     * @param menus    菜单集合
     * @param parentId 父级ID
     * @return 路由信息
     */
    private List<RouterVo> buildRouter(List<SysMenuEntity> menus, Integer parentId) {
        List<RouterVo> routerVos = new ArrayList<>();
        menus.forEach(menu -> {
            if (parentId.equals(menu.getParentId())) {
                RouterVo routerVo = new RouterVo();
                routerVo.setMenuId(menu.getMenuId());
                routerVo.setName(menu.getMenuName());
                routerVo.setPath(menu.getPath());
                routerVo.setComponent(menu.getComponent());
                routerVo.setOrderNum(menu.getOrderNum());
                routerVo.setMeta(new Meta(menu.getIcon(), menu.getTitle(), menu.getIsLink(),
                        menu.getIsHide(), menu.getIsFull(), menu.getIsAffix(), menu.getIsKeepAlive()));
                List<RouterVo> children = buildRouter(menus, menu.getMenuId());
                if (children != null && !children.isEmpty()) {
                    routerVo.setChildren(children);
                }
                routerVos.add(routerVo);
            }
        });
        // 使用匿名内部类作为比较器，根据 orderNum 字段进行升序排序
        Collections.sort(routerVos, new Comparator<RouterVo>() {
            @Override
            public int compare(RouterVo routerVo1, RouterVo routerVo2) {
                return Integer.compare(routerVo1.getOrderNum(), routerVo2.getOrderNum());
            }
        });
        return routerVos;
    }

    /**
     * 递归构造树形菜单信息
     *
     * @param menus    菜单集合
     * @param parentId 父级ID
     * @return 数结构菜单信息
     */
    private List<MenuSelectVo> buildSelect(List<SysMenuEntity> menus, Integer parentId) {
        List<MenuSelectVo> result = new ArrayList<>();
        menus.forEach(menu -> {
            if (parentId.equals(menu.getParentId())) {
                MenuSelectVo selectVo = new MenuSelectVo();
                selectVo.setValue(menu.getMenuId());
                selectVo.setLabel(menu.getTitle());
                selectVo.setMenuType(menu.getMenuType());
                selectVo.setOrderNum(menu.getOrderNum());
                List<MenuSelectVo> children = buildSelect(menus, menu.getMenuId());
                if (children != null && !children.isEmpty()) {
                    selectVo.setChildren(children);
                }
                result.add(selectVo);
            }
        });
        // 使用匿名内部类作为比较器，根据 orderNum 字段进行升序排序
        Collections.sort(result, new Comparator<MenuSelectVo>() {
            @Override
            public int compare(MenuSelectVo routerVo1, MenuSelectVo routerVo2) {
                return Integer.compare(routerVo1.getOrderNum(), routerVo2.getOrderNum());
            }
        });
        return result;
    }

    /**
     * 清空所有用户缓存的菜单数据
     */
    private void clearUserMenuCache() {
        redisCache.fuzzyDelete(UserConstants.SYSTEM_MENU_CACHE + "*");
    }

}
