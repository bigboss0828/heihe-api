package com.transpeed.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hanshuai
 * @title: SysDictType
 * @description: 字典类型实体
 * @date 2023/6/2 16:24
 */
@TableName(value = "sys_dict_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictTypeEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer dictTypeId;

    /**
     * 字典名称
     */
    @TableField("dict_type_name")
    private String dictTypeName;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 状态 （0：正常 1：停用 ）
     */
    @TableField("dict_type_status")
    private Integer dictTypeStatus;

    /**
     * 字典数据
     */
    @TableField(exist = false)
    private List<SysDictDataEntity> dictDataList;

}
