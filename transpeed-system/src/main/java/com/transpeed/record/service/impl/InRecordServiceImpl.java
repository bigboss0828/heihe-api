package com.transpeed.record.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.personnel.service.IPersonnelService;
import com.transpeed.record.common.InRecordSelectDto;
import com.transpeed.record.common.vo.InRecordExportVo;
import com.transpeed.record.entity.InRecordEntity;
import com.transpeed.record.mapper.InRecordMapper;
import com.transpeed.record.service.IInRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: InRecordServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:50
 * @Version 1.0
 */
@Service
public class InRecordServiceImpl implements IInRecordService {

    @Autowired
    private InRecordMapper inRecordMapper;

    @Autowired
    private IPersonnelService personnelService;

    @Override
    public DataGridView listForInRecord(InRecordSelectDto dto) {
        Page<InRecordEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.inRecordMapper.selectPage(page, Wrappers.<InRecordEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getLaneName()), InRecordEntity::getLaneName, dto.getLaneName())
                .like(StringUtils.isNotEmpty(dto.getPlate()), InRecordEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getIcNO()), InRecordEntity::getIcNO, dto.getIcNO())
                .like(StringUtils.isNotEmpty(dto.getObuId()), InRecordEntity::getObuId, dto.getObuId())
                .like(StringUtils.isNotEmpty(dto.getOutType()), InRecordEntity::getOutType, dto.getOutType())
                .eq(StringUtils.isNotEmpty(dto.getCarType()), InRecordEntity::getCarType, dto.getCarType())
                .eq(StringUtils.isNotEmpty(dto.getWhiteType()), InRecordEntity::getWhiteType, dto.getWhiteType())
                .ge(dto.getBeginTime() != null, InRecordEntity::getTriggerTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, InRecordEntity::getTriggerTime, dto.getEndTime())
                .orderByDesc(InRecordEntity::getId)
        );
        List<InRecordEntity> records = page.getRecords();
        for (InRecordEntity record : records) {
            PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(record.getIcNO());
            if (personByIcNo != null) {
                record.setOwnerName(personByIcNo.getOwnerName());
            }
        }
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    @Override
    public List<InRecordExportVo> exportInRecord(InRecordSelectDto dto) {
        List<InRecordEntity> inRecords = this.inRecordMapper.selectList(Wrappers.<InRecordEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getLaneName()), InRecordEntity::getLaneName, dto.getLaneName())
                .like(StringUtils.isNotEmpty(dto.getPlate()), InRecordEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getIcNO()), InRecordEntity::getIcNO, dto.getIcNO())
                .like(StringUtils.isNotEmpty(dto.getObuId()), InRecordEntity::getObuId, dto.getObuId())
                .like(StringUtils.isNotEmpty(dto.getOutType()), InRecordEntity::getOutType, dto.getOutType())
                .eq(StringUtils.isNotEmpty(dto.getCarType()), InRecordEntity::getCarType, dto.getCarType())
                .eq(StringUtils.isNotEmpty(dto.getWhiteType()), InRecordEntity::getWhiteType, dto.getWhiteType())
                .ge(dto.getBeginTime() != null, InRecordEntity::getTriggerTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, InRecordEntity::getTriggerTime, dto.getEndTime())
                .orderByDesc(InRecordEntity::getId)
        );
        ArrayList<InRecordExportVo> result = new ArrayList<>();
        for (InRecordEntity record : inRecords) {
            InRecordExportVo inRecordVo = new InRecordExportVo();
            inRecordVo.setLaneName(record.getLaneName());
            inRecordVo.setPlate(record.getPlate());
            inRecordVo.setObuId(record.getObuId());
            inRecordVo.setIcNO(record.getIcNO());
            if (StringUtils.isNotEmpty(record.getIcNO())) {
                PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(record.getIcNO());
                if (personByIcNo != null) {
                    inRecordVo.setOwnerName(personByIcNo.getOwnerName());
                }
            }
            inRecordVo.setDepartName(record.getDepartName());
            inRecordVo.setWhiteType(record.getWhiteType());
            inRecordVo.setTriggerTime(record.getTriggerTime());
            result.add(inRecordVo);
        }
        return result;
    }
}
