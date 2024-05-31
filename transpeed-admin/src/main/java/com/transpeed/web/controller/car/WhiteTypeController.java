package com.transpeed.web.controller.car;

import com.transpeed.car.common.dto.WhiteTypeSelectDto;
import com.transpeed.car.entity.WhiteTypeEntity;
import com.transpeed.car.service.IWhiteTypeService;
import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: WhiteTypeController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 12:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/car/white/type")
public class WhiteTypeController extends BaseController {

    @Autowired
    private IWhiteTypeService whiteTypeService;

    /**
     * 分页查询白名单类型
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getListForWhiteType(WhiteTypeSelectDto dto) {
        return this.getDataTable(this.whiteTypeService.listForWhiteType(dto));
    }

    /**
     * 查询所有白名单类型
     *
     * @return
     */
    @GetMapping("/allList")
    public AjaxResult getAllWhiteTypeList() {
        return AjaxResult.success(this.whiteTypeService.listForAllWhiteType());
    }

    /**
     * 新增白名单
     *
     * @param whiteTypeEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE_TYPE, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addWhiteType(@RequestBody WhiteTypeEntity whiteTypeEntity) {
        whiteTypeEntity.setCreateBy(SecurityUtils.getUsername());
        int maxWhiteKey = this.whiteTypeService.getMaxWhiteKey();
        whiteTypeEntity.setTypeKey(String.valueOf(maxWhiteKey + 1));
        return this.toAjax(this.whiteTypeService.addWhiteType(whiteTypeEntity));
    }

    /**
     * 更新白名单类型
     *
     * @param whiteTypeEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE_TYPE, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updateWhiteType(@RequestBody WhiteTypeEntity whiteTypeEntity) {
        whiteTypeEntity.setUpdateBy(SecurityUtils.getUsername());
        return this.toAjax(this.whiteTypeService.updateWhiteType(whiteTypeEntity));
    }

    /**
     * 删除白名单类型
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE_TYPE, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delWhiteType(@PathVariable Integer[] ids) {
        return this.toAjax(this.whiteTypeService.delWhiteType(ids));
    }

}
