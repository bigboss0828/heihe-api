package com.transpeed.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.personnel.entity.PersonnelLevelEntity;
import com.transpeed.personnel.mapper.PersonnelLevelMapper;
import com.transpeed.personnel.service.IPersonnelLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @title: PersonnelLevelServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 10:33
 * @Version 1.0
 */
@Service
public class PersonnelLevelServiceImpl implements IPersonnelLevelService {

    @Autowired
    private PersonnelLevelMapper personnelLevelMapper;

    /**
     * 查询所有人员登记列表
     * @return
     */
    @Override
    public List<PersonnelLevelEntity> listForPersonnelLevel() {
        return this.personnelLevelMapper.selectList(Wrappers.<PersonnelLevelEntity>lambdaQuery());
    }

    /**
     * 通过typeKey查询
     * @param typeKey
     * @return
     */
    @Override
    public PersonnelLevelEntity getLevelByKey(String typeKey) {
        return this.personnelLevelMapper.selectOne(Wrappers.<PersonnelLevelEntity>lambdaQuery()
                .eq(PersonnelLevelEntity::getTypeKey, typeKey)
                .last(SystemConstants.SQL_LIMIT)
        );
    }

    /**
     * 新增人员等级
     * @param levelEntity
     * @return
     */
    @Override
    public int addPersonnelLevel(PersonnelLevelEntity levelEntity) {
        return this.personnelLevelMapper.insert(levelEntity);
    }

    /**
     * 更新人员等级
     * @param levelEntity
     * @return
     */
    @Override
    public int updatePersonnelLevel(PersonnelLevelEntity levelEntity) {
        return this.personnelLevelMapper.updateById(levelEntity);
    }

    /**
     * 获取最大的等级数
     * @return
     */
    @Override
    public Integer getMaxLevel() {
        String maxValue = this.personnelLevelMapper.findMaxValue();
        int maxKey = Integer.parseInt(maxValue);
        return maxKey;
    }

    /**
     * 删除人员等级
     * @param ids
     * @return
     */
    @Override
    public int delPersonnelLevel(Integer[] ids) {
        return this.personnelLevelMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
