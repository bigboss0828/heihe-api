package com.transpeed.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.personnel.entity.PersonnelLevelEntity;
import com.transpeed.personnel.service.IPersonnelLevelService;
import com.transpeed.personnel.service.IPersonnelService;
import com.transpeed.record.common.ParkRecordSelectDto;
import com.transpeed.record.common.vo.ParkRecordExportVo;
import com.transpeed.record.entity.InRecordEntity;
import com.transpeed.record.entity.OutRecordEntity;
import com.transpeed.record.entity.ParkRecordEntity;
import com.transpeed.record.mapper.ParkRecordMapper;
import com.transpeed.record.service.IParkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: ParkRecordServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:57
 * @Version 1.0
 */
@Service
public class ParkRecordServiceImpl implements IParkRecordService {

    @Autowired
    private ParkRecordMapper parkRecordMapper;

    @Autowired
    private IPersonnelService personnelService;

    @Autowired
    private IPersonnelLevelService personnelLevelService;

    @Override
    public DataGridView listForParkRecord(ParkRecordSelectDto dto) {
        Page<ParkRecordEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<ParkRecordEntity> out = new QueryWrapper<>();
        out.like(StringUtils.isNotEmpty(dto.getPlate()), "plate", dto.getPlate());
        if (StringUtils.isNotEmpty(dto.getPersonLevel())) {
            if (dto.getPersonLevel().equals("99")) {
                out.isNull("person_level");
            } else {
                out.eq("person_level", dto.getPersonLevel());
            }
        }
        out.like(StringUtils.isNotEmpty(dto.getWhiteType()), "white_type", dto.getWhiteType());
        out.ge(dto.getBeginTime() != null, "in_time", dto.getBeginTime());
        out.le(dto.getEndTime() != null, "out_time", dto.getEndTime());
        out.like(StringUtils.isNotEmpty(dto.getDepartName()), "depart_name", dto.getDepartName());
        out.like(StringUtils.isNotEmpty(dto.getCompany()), "depart_name", dto.getCompany());
        out.orderByDesc("id");
        List<ParkRecordEntity> recordList = this.parkRecordMapper.selectList(out);
        int count = 0;
        for (ParkRecordEntity record : recordList) {
            if (record.getParkTime() != null) {
                count += record.getParkTime();
            }
        }
        this.parkRecordMapper.selectPage(page, out);
        List<ParkRecordEntity> records = page.getRecords();
        for (ParkRecordEntity record : records) {
            record.setTotalTime(count);
            if (StringUtils.isNotEmpty(record.getVoucher())) {
                String[] split = record.getVoucher().split(";");
                if (split[1] != null) {
                    PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(split[1]);
                    if (personByIcNo != null) {
                        record.setOwnerName(personByIcNo.getOwnerName());
                    }
                }
            }
        }
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    @Override
    public List<ParkRecordExportVo> exportParkRecord(ParkRecordSelectDto dto) {
        QueryWrapper<ParkRecordEntity> out = new QueryWrapper<>();
        out.like(StringUtils.isNotEmpty(dto.getPlate()), "plate", dto.getPlate());
        if (StringUtils.isNotEmpty(dto.getPersonLevel())) {
            if (dto.getPersonLevel().equals("99")) {
                out.isNull("person_level");
            } else {
                out.eq("person_level", dto.getPersonLevel());
            }
        }
        out.like(StringUtils.isNotEmpty(dto.getWhiteType()), "white_type", dto.getWhiteType());
        out.ge(dto.getBeginTime() != null, "in_time", dto.getBeginTime());
        out.le(dto.getEndTime() != null, "out_time", dto.getEndTime());
        out.like(StringUtils.isNotEmpty(dto.getDepartName()), "depart_name", dto.getDepartName());
        out.like(StringUtils.isNotEmpty(dto.getCompany()), "depart_name", dto.getCompany());
        out.orderByDesc("id");
        List<ParkRecordEntity> recordList = this.parkRecordMapper.selectList(out);
        ArrayList<ParkRecordExportVo> result = new ArrayList<>();
        for (ParkRecordEntity record : recordList) {
            ParkRecordExportVo exportVo = new ParkRecordExportVo();
            exportVo.setPlate(record.getPlate());
            if (StringUtils.isNotEmpty(record.getVoucher())) {
                String[] split = record.getVoucher().split(";");
                if (split[0] != null) {
                    exportVo.setObu(split[0]);
                }
                if (split[1] != null) {
                    PersonnelEntity personByIcNo = this.personnelService.getPersonByIcNo(split[1]);
                    if (personByIcNo != null) {
                        exportVo.setOwnerName(personByIcNo.getOwnerName());
                    }
                }
            }
            if (StringUtils.isNotEmpty(record.getPersonLevel())) {
                PersonnelLevelEntity level = this.personnelLevelService.getLevelByKey(record.getPersonLevel());
                exportVo.setPersonLevel(level.getTypeName());
            }
            if (record.getDepartName() != null) {
                String[] split = record.getDepartName().split("-");
                if (split.length == 1) {
                    exportVo.setDepartName(split[0]);
                } else {
                    exportVo.setCompany(split[0]);
                    exportVo.setDepartName(split[1]);
                }
            }
            exportVo.setInLaneName(record.getInLaneName());
            exportVo.setInTime(record.getInTime());
            exportVo.setOutLaneName(record.getOutLaneName());
            exportVo.setOutTime(record.getOutTime());
            exportVo.setWhiteType(record.getWhiteType());
            if (record.getParkTime() != null) {
                exportVo.setParkTime(formatSecondsToHMS(record.getParkTime()));
            }
            result.add(exportVo);
        }
        return result;
    }

    public String formatSecondsToHMS(Integer seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remindSeconds = seconds % 60;
//        return hours + ":" + minutes + ":" + remindSeconds;
        return String.format("%02d:%02d:%02d", hours, minutes, remindSeconds);
    }
}
