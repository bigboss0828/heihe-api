package com.transpeed.personnel.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.personnel.common.dto.PersonnelImportDto;
import com.transpeed.personnel.common.dto.PersonnelSelectDto;
import com.transpeed.personnel.common.dto.PersonnelUIDto;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.system.common.dto.user.SysUserDto;

import java.util.List;

/**
 * @title: IPersonnelService
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:23
 * @Version 1.0
 */

public interface IPersonnelService {

    /**
     * 分页查询人员信息
     * @param dto
     * @return
     */
    DataGridView listForPersonnel(PersonnelSelectDto dto);

    /**
     * 新增人员白名单
     * @param personnelEntity
     * @return
     */
    int addPersonnel(PersonnelEntity personnelEntity);

    /**
     * 编辑人员
     * @param personnelEntity
     * @return
     */
    int updatePersonnel(PersonnelEntity personnelEntity);

    /**
     * 删除人员
     * @param ids
     * @return
     */
    int delPersonnel(Integer[] ids);

    /**
     * 验证人员卡号重复
     * @param personnel
     * @return
     */
    String checkIcNoUnique(PersonnelEntity personnel);


    /**
     * 校验身份证号是否重复
     * @param personnel
     * @return
     */
    String checkIdCardUnique(PersonnelEntity personnel);

    PersonnelEntity getPersonByIcNo(String icNo);

    String importPersonnel(List<PersonnelEntity> personnels, PersonnelImportDto dto);


}
