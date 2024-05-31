package com.transpeed.system.common.dto.dict;

import com.transpeed.common.core.dto.BaseDto;
import com.transpeed.system.entity.SysDictDataEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hanshuai
 * @title: SysDictTypeDto
 * @description: 字典类型接收前端对象
 * @date 2023/6/5 11:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictTypeDto extends BaseDto {

    /**
     * 主键ID
     */
    private Integer dictTypeId;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictTypeName;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典状态 （0：正常 1：停用 ）
     */
    private Integer dictTypeStatus;

    /**
     * 字典数据
     */
    @NotNull(message = "字典数据不能为空")
    private List<SysDictDataEntity> dictDataList;

}
