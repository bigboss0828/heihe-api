package com.transpeed.web.controller.park;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.park.common.dto.ParkSelectDto;
import com.transpeed.park.entity.ParkEntity;
import com.transpeed.park.service.IParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: ParkController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/park/park")
public class ParkController extends BaseController {

    @Autowired
    private IParkService parkService;

    /**
     * 分页查询停车场
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getListForPark(ParkSelectDto dto) {
        return this.getDataTable(this.parkService.listForPark(dto));
    }

    @Log(title = BusinessTitle.PARK, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updatePark(@RequestBody ParkEntity parkEntity) {
        parkEntity.setUpdateBy(SecurityUtils.getUsername());
        return this.toAjax(this.parkService.updatePark(parkEntity));
    }

}
