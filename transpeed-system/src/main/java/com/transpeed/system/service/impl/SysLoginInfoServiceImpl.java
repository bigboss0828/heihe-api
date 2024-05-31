package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.system.common.dto.logininfo.SysLoginInfoDto;
import com.transpeed.system.entity.SysLoginInfoEntity;
import com.transpeed.system.mapper.SysLoginInfoMapper;
import com.transpeed.system.service.ISysLoginInfoService;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hanshuai
 * @title: SysLoginInfoServiceImpl
 * @description: 登录日志业务层
 * @date 2023/6/1 09:59
 */
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfoEntity> implements ISysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 查询登录日志表格数据
     *
     * @param loginInfoDto 查询条件
     * @return 表格数据
     */
    @Override
    public DataGridView listForLoginInfo(SysLoginInfoDto loginInfoDto) {
        Page<SysLoginInfoEntity> page = new Page<>(loginInfoDto.getPageNum(), loginInfoDto.getPageSize());
        this.loginInfoMapper.selectPage(page, Wrappers.<SysLoginInfoEntity>lambdaQuery()
                // 用户名称
                .like(StringUtils.isNotEmpty(loginInfoDto.getUserName()), SysLoginInfoEntity::getUserName, loginInfoDto.getUserName())
                // 登录状态
                .eq(loginInfoDto.getLoginStatus() != null, SysLoginInfoEntity::getLoginStatus, loginInfoDto.getLoginStatus())
                // 登录时间
                .ge(loginInfoDto.getBeginTime() != null, SysLoginInfoEntity::getLoginTime, loginInfoDto.getBeginTime())
                .le(loginInfoDto.getEndTime() != null, SysLoginInfoEntity::getLoginTime, loginInfoDto.getEndTime())
                // ID倒序
                .orderByDesc(SysLoginInfoEntity::getLoginInfoId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 插入登陆日志
     *
     * @param loginInfo 登录日志实体
     */
    @Override
    public void insert(SysLoginInfoEntity loginInfo) {
        this.loginInfoMapper.insert(loginInfo);
    }

}
