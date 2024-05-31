package com.transpeed.personnel.service;

import com.transpeed.personnel.entity.PersonnelLevelEntity;

import java.util.List;

/**
 * @title: IPersonnelLevelService
 * @Author zhangwenxiang
 * @Date: 2023/9/14 10:33
 * @Version 1.0
 */

public interface IPersonnelLevelService {

    /**
     * 查询所有人员登记列表
     * @return
     */
    List<PersonnelLevelEntity> listForPersonnelLevel();

    /**
     * 通过typeKey查询
     * @param typeKey
     * @return
     */
    PersonnelLevelEntity getLevelByKey(String typeKey);

    /**
     * 新增人员等级
     * @param levelEntity
     * @return
     */
    int addPersonnelLevel(PersonnelLevelEntity levelEntity);

    /**
     * 更新人员等级
     * @param levelEntity
     * @return
     */
    int updatePersonnelLevel(PersonnelLevelEntity levelEntity);

    /**
     * 获取最大的等级数
     * @return
     */
    Integer getMaxLevel();

    /**
     * 删除人员等级
     * @param ids
     * @return
     */
    int delPersonnelLevel(Integer[] ids);

}
