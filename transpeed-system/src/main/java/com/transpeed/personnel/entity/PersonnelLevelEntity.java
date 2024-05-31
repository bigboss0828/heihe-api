package com.transpeed.personnel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: PersonnelLevelEntity
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 10:29
 * @Version 1.0
 */
@TableName("p_person_level")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelLevelEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 停车场编码
     */
    @TableField("park_code")
    private String parkCode;

    /**
     * 级别名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 级别编码 见数据字典（0：T1  1：T2  2：T3  3:T4）
     */
    @TableField("type_key")
    private String typeKey;

}
