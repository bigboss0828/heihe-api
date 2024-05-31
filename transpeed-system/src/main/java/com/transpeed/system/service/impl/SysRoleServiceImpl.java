package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.constant.business.system.role.RoleConstants;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.constant.system.SystemStatus;
import com.transpeed.common.core.domain.entity.SysRoleEntity;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.system.common.dto.role.SysRoleDto;
import com.transpeed.system.entity.SysRoleMenuEntity;
import com.transpeed.system.entity.SysUserRoleEntity;
import com.transpeed.system.mapper.SysRoleMapper;
import com.transpeed.system.mapper.SysRoleMenuMapper;
import com.transpeed.system.mapper.SysUserRoleMapper;
import com.transpeed.system.service.ISysMenuService;
import com.transpeed.system.service.ISysRoleService;
import com.transpeed.system.service.ISysUserService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.common.utils.business.SysUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hanshuai
 * @title: SysRoleServiceImpl
 * @description: 角色业务层
 * @date 2023/6/1 14:40
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询角色表格数据
     *
     * @param roleDto 查询条件
     * @return 表格数据
     */
    @Override
    public DataGridView listForRole(SysRoleDto roleDto) {
        Page<SysRoleEntity> page = new Page<>(roleDto.getPageNum(), roleDto.getPageSize());
        this.roleMapper.selectPage(page, Wrappers.<SysRoleEntity>lambdaQuery()
                // 角色名称
                .like(StringUtils.isNotEmpty(roleDto.getRoleName()), SysRoleEntity::getRoleName, roleDto.getRoleName())
                // 角色状态
                .eq(roleDto.getRoleStatus() != null, SysRoleEntity::getRoleStatus, roleDto.getRoleStatus())
                // ID正序
                .orderByAsc(SysRoleEntity::getRoleId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 获取角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @Override
    public SysRoleEntity getInfoRole(Integer roleId) {
        SysRoleEntity role = roleMapper.selectById(roleId);
        List<Integer> menuIdList = roleMenuMapper.selectList(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                        .eq(SysRoleMenuEntity::getRoleId, roleId)
                ).stream()
                .map(SysRoleMenuEntity::getMenuId)
                .collect(Collectors.toList());
        role.setMenuIdList(menuIdList);
        return role;
    }

    /**
     * 添加角色信息
     *
     * @param roleDto 角色实体
     * @return 添加结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addRole(SysRoleDto roleDto) {
        // 添加角色信息
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDto, roleEntity);
        roleEntity.setCreateBy(SecurityUtils.getUsername());
        roleEntity.setRoleStatus(SystemStatus.OK.getCode());
        int roleCount = this.roleMapper.insert(roleEntity);

        // 添加角色菜单关联信息
        this.batchAddRoleMenu(roleDto.getMenuIdList(), roleEntity.getRoleId());

        return roleCount;
    }

    /**
     * 修改角色信息
     *
     * @param roleDto 角色实体
     * @return 修改结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateRole(SysRoleDto roleDto) {
        // 删除角色与菜单绑定表内此角色的所有信息
        this.roleMenuMapper.delete(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                .eq(SysRoleMenuEntity::getRoleId, roleDto.getRoleId())
        );
        // 重新添加角色与菜单绑定信息
        this.batchAddRoleMenu(roleDto.getMenuIdList(), roleDto.getRoleId());

        // 修改角色
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDto, roleEntity);
        roleEntity.setUpdateBy(SecurityUtils.getUsername());

        // 查询角色用户关联表
        List<Integer> userIdList = this.userRoleMapper.selectList(Wrappers.<SysUserRoleEntity>lambdaQuery()
                        .eq(SysUserRoleEntity::getRoleId, roleDto.getRoleId())
                ).stream()
                .map(SysUserRoleEntity::getUserId)
                .collect(Collectors.toList());
        userIdList.forEach(userId -> {
            // 删除修改对应用户的菜单缓存信息
            redisCache.deleteObject(UserConstants.SYSTEM_MENU_CACHE + this.userService.getById(userId).getUserName());
        });

        return this.roleMapper.updateById(roleEntity);
    }

    /**
     * 删除角色数据
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delRole(Integer roleId) {
        this.roleMenuMapper.delete(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                .eq(SysRoleMenuEntity::getRoleId, roleId)
        );
        return this.roleMapper.deleteById(roleId);
    }

    /**
     * 获取所有可用角色集合
     *
     * @return 角色集合
     */
    @Override
    public List<SysRoleEntity> getSelectRole() {
        return this.roleMapper.selectList(Wrappers.<SysRoleEntity>lambdaQuery()
                .eq(SysRoleEntity::getRoleStatus, SystemStatus.OK.getCode())
        );
    }

    /**
     * 校验角色名称是否存在
     *
     * @param roleDto 角色实体
     * @return 是否存在
     */
    @Override
    public String checkRoleNameUnique(SysRoleDto roleDto) {
        Integer id = StringUtils.isNull(roleDto.getRoleId()) ? -1 : roleDto.getRoleId();
        SysRoleEntity roleEntity = this.lambdaQuery()
                .eq(SysRoleEntity::getRoleName, roleDto.getRoleName())
                .last(SystemConstants.SQL_LIMIT)
                .one();
        if (StringUtils.isNotNull(roleEntity) && roleEntity.getRoleId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 查询角色是否绑定用户
     *
     * @param roleId 角色ID
     * @return 是否绑定
     */
    @Override
    public int roleHasUser(Integer roleId) {
        // 查询角色用户关联表
        return this.userRoleMapper.selectCount(Wrappers.<SysUserRoleEntity>lambdaQuery()
                .eq(SysUserRoleEntity::getRoleId, roleId)
        );
    }

    /**
     * 用户登录 根据用户ID查询对应角色的所有按钮权限
     *
     * @param userId 用户ID
     * @return 按钮权限
     */
    @Override
    public Set<String> getBtnByUserId(Integer userId) {
        // 查询角色用户关联表
        SysUserEntity user = userService.getInfoUser(userId);
        if (!SysUtils.isAdmin(user.getUserType()) && StringUtils.isEmpty(user.getRoleIdList())) {
            throw new ServiceException(RoleConstants.USER_NOT_HAS_ROLE);
        }
        List<Integer> menuIdList = this.roleMenuMapper.selectList(Wrappers.<SysRoleMenuEntity>lambdaQuery()
                        .in(!SysUtils.isAdmin(user.getUserType()), SysRoleMenuEntity::getRoleId, user.getRoleIdList())
                ).stream()
                .map(SysRoleMenuEntity::getMenuId)
                .collect(Collectors.toList());
        return this.menuService.getMenusByIdList(menuIdList, SysUtils.isAdmin(user.getUserType()));
    }

    /**
     * 批量添加角色与菜单信息
     *
     * @param menuIds 菜单ID集合
     * @param roleId  角色ID
     */
    private void batchAddRoleMenu(List<Integer> menuIds, Integer roleId) {
        List<SysRoleMenuEntity> roleMenuList = menuIds.stream()
                .map(menuId -> {
                    SysRoleMenuEntity roleMenuEntity = new SysRoleMenuEntity();
                    roleMenuEntity.setRoleId(roleId);
                    roleMenuEntity.setMenuId(menuId);
                    return roleMenuEntity;
                })
                .collect(Collectors.toList());
        this.roleMenuMapper.batchAdd(roleMenuList);
    }

}
