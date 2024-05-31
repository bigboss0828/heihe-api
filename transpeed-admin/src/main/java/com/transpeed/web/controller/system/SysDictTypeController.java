package com.transpeed.web.controller.system;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.system.common.dto.dict.SysDictTypeDto;
import com.transpeed.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author hanshuai
 * @title: SysDictTypeController
 * @description: 字典管理
 * @date 2023/6/5 11:46
 */
@RestController
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {

    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 查询字典表格数据
     *
     * @param dictTypeDto 查询条件
     * @return 分页数据
     */
    @GetMapping("/listForDictType")
    public AjaxResult listForDictType(SysDictTypeDto dictTypeDto) {
        return this.getDataTable(this.dictTypeService.listForDictType(dictTypeDto));
    }

    /**
     * 查询字典数据信息
     *
     * @param dictTypeId 字典ID
     * @return 字典数据
     */
    @GetMapping(value = "/{dictTypeId}")
    public AjaxResult getInfoDictType(@PathVariable Integer dictTypeId) {
        return AjaxResult.success(this.dictTypeService.getInfoDictType(dictTypeId));
    }

    /**
     * 新增字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 新增结果
     */
    @PreAuthorize("@ss.hasPermi('sys:dict:add')")
    @Log(title = BusinessTitle.SYSTEM_DICT, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addDictType(@Validated @RequestBody SysDictTypeDto dictTypeDto) {
        // 校验字典类型名称是否存在
        if (SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictNameUnique(dictTypeDto))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典名称已经存在！");
        }
        // 校验字典类型是否存在
        if (SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictTypeUnique(dictTypeDto))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典类型已经存在！");
        }
        // 校验字典值标签是否存在
        if (!this.dictTypeService.validateDictDataList(dictTypeDto.getDictDataList()) || SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictDataLabelUnique(dictTypeDto.getDictDataList()))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典值标签重复或存在空值！");
        }
        // 校验字典值的值是否存在
        if (!this.dictTypeService.validateDictDataList(dictTypeDto.getDictDataList()) || SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictDataValueUnique(dictTypeDto.getDictDataList()))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典值重复或存在空值！");
        }
        return this.toAjax(this.dictTypeService.addDictType(dictTypeDto));
    }

    /**
     * 修改字典数据
     *
     * @param dictTypeDto 字典数据
     * @return 修改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:dict:update')")
    @Log(title = BusinessTitle.SYSTEM_DICT, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateDictType(@Validated @RequestBody SysDictTypeDto dictTypeDto) {
        // 校验字典类型名称是否存在
        if (SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictNameUnique(dictTypeDto))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典名称已经存在！");
        }
        // 校验字典类型是否存在
        if (SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictTypeUnique(dictTypeDto))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典类型已经存在！");
        }
        // 校验字典值标签是否存在
        if (!this.dictTypeService.validateDictDataList(dictTypeDto.getDictDataList()) || SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictDataLabelUnique(dictTypeDto.getDictDataList()))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典值标签重复或存在空值！");
        }
        // 校验字典值的值是否存在
        if (!this.dictTypeService.validateDictDataList(dictTypeDto.getDictDataList()) || SystemConstants.NOT_UNIQUE.equals(this.dictTypeService.checkDictDataValueUnique(dictTypeDto.getDictDataList()))) {
            return AjaxResult.error("新增字典'" + dictTypeDto.getDictTypeName() + "'失败，字典值重复或存在空值！");
        }
        return this.toAjax(this.dictTypeService.updateDictType(dictTypeDto));
    }

    /**
     * 删除字典数据
     *
     * @param dictTypeId 字典ID
     * @return 删除结果
     */
    @PreAuthorize("@ss.hasPermi('sys:dict:del')")
    @Log(title = BusinessTitle.SYSTEM_DICT, businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{dictTypeId}")
    public AjaxResult delDictType(@PathVariable Integer dictTypeId) {
        return this.toAjax(this.dictTypeService.delDictType(dictTypeId));
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据
     */
    @GetMapping(value = "/getDictData/{dictType}")
    public AjaxResult getDictData(@PathVariable String dictType) {
        return AjaxResult.success(this.dictTypeService.getDictData(dictType));
    }

}
