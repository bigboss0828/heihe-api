package com.transpeed.record.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.personnel.service.IPersonnelService;
import com.transpeed.record.common.OutRecordSelectDto;
import com.transpeed.record.common.vo.InRecordExportVo;
import com.transpeed.record.common.vo.OutRecordExportVo;
import com.transpeed.record.entity.InRecordEntity;
import com.transpeed.record.entity.OutRecordEntity;
import com.transpeed.record.mapper.OutRecordMapper;
import com.transpeed.record.service.IOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: OutRecordServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:30
 * @Version 1.0
 */
@Service
public class OutRecordServiceImpl implements IOutRecordService {

    @Autowired
    private OutRecordMapper outRecordMapper;

    @Autowired
    private IPersonnelService personnelService;

    @Override
    public DataGridView listForOutRecord(OutRecordSelectDto dto) {
        Page<OutRecordEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.outRecordMapper.selectPage(page, Wrappers.<OutRecordEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getLaneName()), OutRecordEntity::getLaneName, dto.getLaneName())
                .like(StringUtils.isNotEmpty(dto.getPlate()), OutRecordEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getIcNO()), OutRecordEntity::getIcNO, dto.getIcNO())
                .like(StringUtils.isNotEmpty(dto.getObuId()), OutRecordEntity::getObuId, dto.getObuId())
                .eq(StringUtils.isNotEmpty(dto.getCarType()), OutRecordEntity::getCarType, dto.getCarType())
                .eq(StringUtils.isNotEmpty(dto.getWhiteType()), OutRecordEntity::getWhiteType, dto.getWhiteType())
                .ge(dto.getBeginTime() != null, OutRecordEntity::getTriggerTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, OutRecordEntity::getTriggerTime, dto.getEndTime())
                .orderByDesc(OutRecordEntity::getId)
        );
        List<OutRecordEntity> records = page.getRecords();
        for (OutRecordEntity record : records) {
            PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(record.getIcNO());
            if (personByIcNo != null) {
                record.setOwnerName(personByIcNo.getOwnerName());
            }
        }
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    @Override
    public List<OutRecordExportVo> exportOutRecord(OutRecordSelectDto dto) {
        List<OutRecordEntity> outRecords = this.outRecordMapper.selectList(Wrappers.<OutRecordEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getLaneName()), OutRecordEntity::getLaneName, dto.getLaneName())
                .like(StringUtils.isNotEmpty(dto.getPlate()), OutRecordEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getIcNO()), OutRecordEntity::getIcNO, dto.getIcNO())
                .like(StringUtils.isNotEmpty(dto.getObuId()), OutRecordEntity::getObuId, dto.getObuId())
                .eq(StringUtils.isNotEmpty(dto.getCarType()), OutRecordEntity::getCarType, dto.getCarType())
                .eq(StringUtils.isNotEmpty(dto.getWhiteType()), OutRecordEntity::getWhiteType, dto.getWhiteType())
                .ge(dto.getBeginTime() != null, OutRecordEntity::getTriggerTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, OutRecordEntity::getTriggerTime, dto.getEndTime())
                .orderByDesc(OutRecordEntity::getId)
        );
        ArrayList<OutRecordExportVo> result = new ArrayList<>();
        for (OutRecordEntity record : outRecords) {
            OutRecordExportVo outRecordVo = new OutRecordExportVo();
            outRecordVo.setLaneName(record.getLaneName());
            outRecordVo.setPlate(record.getPlate());
            outRecordVo.setObuId(record.getObuId());
            outRecordVo.setIcNO(record.getIcNO());
            if (StringUtils.isNotEmpty(record.getIcNO())) {
                PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(record.getIcNO());
                if (personByIcNo != null) {
                    outRecordVo.setOwnerName(personByIcNo.getOwnerName());
                }
            }
            outRecordVo.setDepartName(record.getDepartName());
            outRecordVo.setWhiteType(record.getWhiteType());
            outRecordVo.setTriggerTime(record.getTriggerTime());
            result.add(outRecordVo);
        }
        return result;
    }
}
