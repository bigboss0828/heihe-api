package com.transpeed.web.controller.car;

import com.transpeed.car.common.dto.BlackSelectDto;
import com.transpeed.car.entity.BlackEntity;
import com.transpeed.car.service.IBlackService;
import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: BlackController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 15:40
 * @Version 1.0
 */
@RestController
@RequestMapping("/car/black")
public class BlackController extends BaseController {

    @Autowired
    private IBlackService blackService;

    /**
     * 分页查询黑名单车辆
     *
     * @param dto
     * @return
     */
    @GetMapping
    public AjaxResult listForBlack(BlackSelectDto dto) {
        return this.getDataTable(this.blackService.listForBlack(dto));
    }

    /**
     * 新增黑名单车辆
     *
     * @param blackEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_BLACK, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addBlackCar(@RequestBody BlackEntity blackEntity) {
        if (SystemConstants.NOT_UNIQUE.equals(this.blackService.checkPlateUnique(blackEntity))) {
            return AjaxResult.error("新增失败，车牌或者OBUID已存在");
        }
        blackEntity.setCreateBy(SecurityUtils.getUsername());
        return this.toAjax(this.blackService.addBlackCar(blackEntity));
    }

    /**
     * 更新黑名单
     *
     * @param blackEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_BLACK, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateBlackCar(@RequestBody BlackEntity blackEntity) {
        if (SystemConstants.NOT_UNIQUE.equals(this.blackService.checkPlateUnique(blackEntity))) {
            return AjaxResult.error("新增失败，车牌或者OBUID已存在");
        }
        blackEntity.setUpdateBy(SecurityUtils.getUsername());
        return this.toAjax(this.blackService.updateBlackCar(blackEntity));
    }

    /**
     * 删除黑名单车辆
     *
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.CAR_BLACK, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delWhiteCar(@PathVariable Integer[] ids) {
        return this.toAjax(this.blackService.delBlackCar(ids));
    }

}
