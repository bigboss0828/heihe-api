package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.business.system.user.UserType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.constant.system.SystemStatus;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.system.common.dto.user.SysUserDto;
import com.transpeed.system.entity.SysUserRoleEntity;
import com.transpeed.system.mapper.SysUserMapper;
import com.transpeed.system.mapper.SysUserRoleMapper;
import com.transpeed.system.service.ISysUserService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.common.utils.business.SysUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanshuai
 * @title: SysUserServiceImpl
 * @description: 系统用户业务层
 * @date 2023/6/1 08:39
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询用户表格数据
     *
     * @param userDto 查询条件
     * @return 表格数据
     */
    @Override
    public DataGridView listForUser(SysUserDto userDto) {
        Page<SysUserEntity> page = new Page<>(userDto.getPageNum(), userDto.getPageSize());
        this.userMapper.selectPage(page, Wrappers.<SysUserEntity>lambdaQuery()
                // 如果当前登录的用户不为超级管理员 则过滤掉超级管理员数据
                .ne(!SysUtils.isAdmin(SecurityUtils.getUserType()), SysUserEntity::getUserType, UserType.ADMIN.getCode())
                // 登录账号
                .like(StringUtils.isNotEmpty(userDto.getUserName()), SysUserEntity::getUserName, userDto.getUserName())
                // 用户类型
                .eq(userDto.getUserType() != null, SysUserEntity::getUserType, userDto.getUserType())
                // 用户昵称
                .like(StringUtils.isNotEmpty(userDto.getNickName()), SysUserEntity::getNickName, userDto.getNickName())
                // 账号状态
                .like(userDto.getUserStatus() != null, SysUserEntity::getUserStatus, userDto.getUserStatus())
                // ID正序
                .orderByAsc(SysUserEntity::getUserId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserEntity getInfoUser(Integer userId) {
        SysUserEntity userEntity = this.userMapper.selectById(userId);
        List<Integer> roleIdList = this.userRoleMapper.selectList(Wrappers.<SysUserRoleEntity>lambdaQuery()
                        .eq(SysUserRoleEntity::getUserId, userId)
                ).stream()
                .map(SysUserRoleEntity::getRoleId)
                .collect(Collectors.toList());
        if (roleIdList.size() > 0) {
            userEntity.setRoleIdList(roleIdList);
        }
        return userEntity;
    }

    /**
     * 添加用户信息
     *
     * @param userDto 用户实体
     * @return 添加结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addUser(SysUserDto userDto) {
        // 添加用户信息
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setCreateBy(SecurityUtils.getUsername());
        userEntity.setPassword(SecurityUtils.encryptPassword(userDto.getPassword()));
        userEntity.setUserStatus(SystemStatus.OK.getCode());
        int addCount = this.userMapper.insert(userEntity);

        // 添加用户角色关联信息
        this.batchAddUserRole(userDto.getRoleIdList(), userEntity.getUserId());

        return addCount;
    }

    /**
     * 修改用户信息
     *
     * @param userDto 用户实体
     * @return 修改结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(SysUserDto userDto) {
        // 删除用户与角色绑定表内此角色的所有信息
        this.userRoleMapper.delete(Wrappers.<SysUserRoleEntity>lambdaQuery()
                .eq(SysUserRoleEntity::getUserId, userDto.getUserId())
        );
        // 重新添加角色与菜单绑定信息
        this.batchAddUserRole(userDto.getRoleIdList(), userDto.getUserId());

        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setUpdateBy(SecurityUtils.getUsername());

        // 删除修改对应用户的菜单缓存信息
        redisCache.deleteObject(UserConstants.SYSTEM_MENU_CACHE + this.userMapper.selectById(userDto.getUserId()).getUserName());
        return this.userMapper.updateById(userEntity);
    }

    /**
     * 删除用户数据
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUser(Integer userId) {
        this.userRoleMapper.delete(Wrappers.<SysUserRoleEntity>lambdaQuery()
                .eq(SysUserRoleEntity::getUserId, userId)
        );
        return this.userMapper.deleteById(userId);
    }

    /**
     * 校验用户名称是否存在
     *
     * @param userDto 用户实体
     * @return 是否存在
     */
    @Override
    public String checkUserNameUnique(SysUserDto userDto) {
        Integer id = StringUtils.isNull(userDto.getUserId()) ? -1 : userDto.getUserId();
        SysUserEntity userEntity = this.lambdaQuery()
                .eq(SysUserEntity::getUserName, userDto.getUserName())
                .last(SystemConstants.SQL_LIMIT)
                .one();
        if (StringUtils.isNotNull(userEntity) && userEntity.getUserId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public SysUserEntity selectUserByUserName(String username) {
        return this.userMapper.selectOne(Wrappers.<SysUserEntity>lambdaQuery()
                .eq(SysUserEntity::getUserName, username)
        );
    }

    /**
     * 批量添加用户与角色绑定信息
     *
     * @param roleIdList 角色集合
     * @param userId     用户ID
     */
    private void batchAddUserRole(List<Integer> roleIdList, Integer userId) {
        List<SysUserRoleEntity> userRoleList = roleIdList.stream()
                .map(roleId -> {
                    SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                    userRoleEntity.setUserId(userId);
                    userRoleEntity.setRoleId(roleId);
                    return userRoleEntity;
                })
                .collect(Collectors.toList());
        this.userRoleMapper.batchAdd(userRoleList);
    }

}
