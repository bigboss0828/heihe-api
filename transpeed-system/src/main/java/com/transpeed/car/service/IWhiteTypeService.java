package com.transpeed.car.service;

import com.transpeed.car.common.dto.WhiteTypeSelectDto;
import com.transpeed.car.entity.WhiteTypeEntity;
import com.transpeed.common.core.page.DataGridView;

import java.util.List;

/**
 * @title: IWhiteTypeService
 * @Author zhangwenxiang
 * @Date: 2023/9/15 12:12
 * @Version 1.0
 */

public interface IWhiteTypeService {

    /**
     * 分页查询白名单类型
     * @param dto
     * @return
     */
    DataGridView listForWhiteType(WhiteTypeSelectDto dto);

    /**
     * 查询所有白名单类型
     * @return
     */
    List<WhiteTypeEntity> listForAllWhiteType();

    /**
     * 新增白名单类型
     * @param whiteTypeEntity
     * @return
     */
    int addWhiteType(WhiteTypeEntity whiteTypeEntity);

    /**
     * 获取最大key
     * @return
     */
    int getMaxWhiteKey();

    /**
     * 更新白名单类型
     * @param whiteTypeEntity
     * @return
     */
    int updateWhiteType(WhiteTypeEntity whiteTypeEntity);

    int delWhiteType(Integer[] ids);

}
