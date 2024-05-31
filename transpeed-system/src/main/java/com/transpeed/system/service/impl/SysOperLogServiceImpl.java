package com.transpeed.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.system.common.dto.operlog.SysOperLogDto;
import com.transpeed.system.entity.SysLoginInfoEntity;
import com.transpeed.system.entity.SysOperLogEntity;
import com.transpeed.system.mapper.SysOperLogMapper;
import com.transpeed.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hanshuai
 * @title: SysOperLogServiceImpl
 * @description: 操作日志业务层
 * @date 2023/6/19 10:31
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLogEntity> implements ISysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 查询操作日志表格数据
     *
     * @param operLogDto 查询条件
     * @return 表格数据
     */
    @Override
    public DataGridView listForOperLog(SysOperLogDto operLogDto) {
        Page<SysOperLogEntity> page = new Page<>(operLogDto.getPageNum(), operLogDto.getPageSize());
        this.operLogMapper.selectPage(page, Wrappers.<SysOperLogEntity>lambdaQuery()
                // 模块标题
                .eq(operLogDto.getTitle() != null, SysOperLogEntity::getTitle, operLogDto.getTitle())
                // 业务类型
                .eq(operLogDto.getBusinessType() != null, SysOperLogEntity::getBusinessType, operLogDto.getBusinessType())
                // 操作人员
                .like(StringUtils.isNotEmpty(operLogDto.getOperName()), SysOperLogEntity::getOperName, operLogDto.getOperName())
                // 操作状态
                .eq(operLogDto.getOperStatus() != null, SysOperLogEntity::getOperStatus, operLogDto.getOperStatus())
                // 操作时间
                .ge(operLogDto.getBeginTime() != null, SysOperLogEntity::getOperTime, operLogDto.getBeginTime())
                .le(operLogDto.getEndTime() != null, SysOperLogEntity::getOperTime, operLogDto.getEndTime())
                // ID倒序
                .orderByDesc(SysOperLogEntity::getOperId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 插入操作日志
     *
     * @param operLog 日志实体
     */
    @Override
    public void insertOperlog(SysOperLogEntity operLog) {
        this.operLogMapper.insert(operLog);
    }

}
