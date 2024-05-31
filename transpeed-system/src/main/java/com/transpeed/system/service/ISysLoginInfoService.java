package com.transpeed.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.system.common.dto.logininfo.SysLoginInfoDto;
import com.transpeed.system.entity.SysLoginInfoEntity;

/**
 * @author hanshuai
 * @title: ISysLoginInfoService
 * @description: 登录日志业务层
 * @date 2023/6/1 09:59
 */
public interface ISysLoginInfoService extends IService<SysLoginInfoEntity> {

    /**
     * 查询登录日志表格数据
     *
     * @param loginInfoDto 查询条件
     * @return 表格数据
     */
    DataGridView listForLoginInfo(SysLoginInfoDto loginInfoDto);

    /**
     * 插入登陆日志
     *
     * @param loginInfo 登录日志实体
     */
    void insert(SysLoginInfoEntity loginInfo);

}
