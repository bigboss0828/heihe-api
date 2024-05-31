package com.transpeed.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.personnel.common.dto.PersonnelImportDto;
import com.transpeed.personnel.common.dto.PersonnelSelectDto;
import com.transpeed.personnel.common.dto.PersonnelUIDto;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.personnel.mapper.PersonnelMapper;
import com.transpeed.personnel.service.IPersonnelService;
import com.transpeed.system.common.dto.user.SysUserDto;
import com.transpeed.system.entity.SysOperLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @title: PersonnelServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:23
 * @Version 1.0
 */
@Slf4j
@Service
public class PersonnelServiceImpl implements IPersonnelService {

    @Autowired
    private PersonnelMapper personnelMapper;

    /**
     * 分页查询人员表
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForPersonnel(PersonnelSelectDto dto) {
        Page<PersonnelEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.personnelMapper.selectPage(page, Wrappers.<PersonnelEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getOwnerName()), PersonnelEntity::getOwnerName, dto.getOwnerName())
                .like(StringUtils.isNotEmpty(dto.getIcNo()), PersonnelEntity::getIcNo, dto.getIcNo())
                .like(StringUtils.isNotEmpty(dto.getIdCard()), PersonnelEntity::getIdCard, dto.getIdCard())
                .eq(StringUtils.isNotEmpty(dto.getPersonLevel()), PersonnelEntity::getPersonLevel, dto.getPersonLevel())
                .eq(StringUtils.isNotEmpty(dto.getIcStatus()), PersonnelEntity::getIcStatus, dto.getIcStatus())
                .ge(dto.getBeginTime() != null, PersonnelEntity::getStartTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, PersonnelEntity::getEndTime, dto.getEndTime())
                .orderByDesc(PersonnelEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 新增人员
     * @param personnelEntity
     * @return
     */
    @Override
    public int addPersonnel(PersonnelEntity personnelEntity) {
        return this.personnelMapper.insert(personnelEntity);
    }

    /**
     * 编辑人员
     * @param personnelEntity
     * @return
     */
    @Override
    public int updatePersonnel(PersonnelEntity personnelEntity) {
        return this.personnelMapper.updateById(personnelEntity);
    }

    /**
     * 删除人员
     * @param ids
     * @return
     */
    @Override
    public int delPersonnel(Integer[] ids) {
        return this.personnelMapper.deleteBatchIds(Arrays.asList(ids));
    }


    /**
     * 校验有无使用中的卡号重复
     * @param personnelUIDto
     * @return
     */
    @Override
    public String checkIcNoUnique(PersonnelEntity personnel) {
        Integer id = StringUtils.isNull(personnel.getId()) ? -1 : personnel.getId();
        PersonnelEntity personnelEntity = this.personnelMapper.selectOne(Wrappers.<PersonnelEntity>lambdaQuery()
                .eq(PersonnelEntity::getIcNo, personnel.getIcNo())
                .eq(PersonnelEntity::getIcStatus, "0")
                .last(SystemConstants.SQL_LIMIT)
        );
        if (StringUtils.isNotNull(personnelEntity) && personnelEntity.getId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     * 校验身份证号是否重复
     * @param personnel
     * @return
     */
    @Override
    public String checkIdCardUnique(PersonnelEntity personnel) {
        Integer id = StringUtils.isNull(personnel.getId()) ? -1 : personnel.getId();
        PersonnelEntity personnelEntity = this.personnelMapper.selectOne(Wrappers.<PersonnelEntity>lambdaQuery()
                .eq(PersonnelEntity::getIdCard, personnel.getIdCard())
                .last(SystemConstants.SQL_LIMIT)
        );
        if (StringUtils.isNotNull(personnelEntity) && personnelEntity.getId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    /**
     *
     * @param icNo
     * @return 根据ic卡号查询人信息
     */
    @Override
    public PersonnelEntity getPersonByIcNo(String icNo) {
        return this.personnelMapper.selectOne(Wrappers.<PersonnelEntity>lambdaQuery()
                .eq(PersonnelEntity::getIcNo, icNo)
                .last(SystemConstants.SQL_LIMIT)
        );
    }

    @Override
    public String importPersonnel(List<PersonnelEntity> personnels, PersonnelImportDto dto) {
        if (StringUtils.isNull(personnels) || personnels.size() == 0) {
            throw new ServiceException("导入人员数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PersonnelEntity personnel : personnels) {
            try {
                // 校验身份证
                if (StringUtils.isNotEmpty(personnel.getIdCard())) {
                    if (SystemConstants.NOT_UNIQUE.equals(this.checkIdCardUnique(personnel))) {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、 导入人员" + personnel.getOwnerName() + "失败！当前身份证已经存在！");
                        continue;
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、 导入人员" + personnel.getOwnerName() + "失败！身份证号不能为空！");
                    continue;
                }
                if (StringUtils.isNotEmpty(personnel.getIcNo())) {
                    if (SystemConstants.NOT_UNIQUE.equals(this.checkIcNoUnique(personnel))) {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、 导入人员" + personnel.getOwnerName() + "失败！当前卡号已经存在！");
                        continue;
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、 导入人员" + personnel.getOwnerName() + "失败！卡号不能为空！");
                    continue;
                }
                personnel.setDepartName(dto.getDepartName());
                personnel.setPersonLevel(dto.getPersonLevel());
                personnel.setParkCode(SecurityUtils.getCurrentPark());
                personnel.setIcStatus("0");
                this.personnelMapper.insert(personnel);
                successNum++;
                successMsg.append("<br/>" + successNum + "、 导入人员" + personnel.getOwnerName() + "成功！");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、 导入人员" + personnel.getOwnerName() + "失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        return successMsg.toString();
    }
}
