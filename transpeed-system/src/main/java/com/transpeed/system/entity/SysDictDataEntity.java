package com.transpeed.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysDictData
 * @description: 字典值实体
 * @date 2023/6/2 16:33
 */
@TableName(value = "sys_dict_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictDataEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer dictDataId;

    /**
     * 字典标签
     */
    @TableField("dict_data_label")
    private String dictDataLabel;


    /**
     * 字典键值
     */
    @TableField("dict_data_value")
    private Integer dictDataValue;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 字典排序
     */
    @TableField("order_num")
    private Integer orderNum;

}
