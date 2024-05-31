package com.transpeed.web.controller.park;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.park.common.dto.ParkLaneSelectDto;
import com.transpeed.park.entity.ParkLaneEntity;
import com.transpeed.park.service.IParkLaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @title: ParkLaneController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/15 11:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/park/lane")
public class ParkLaneController extends BaseController {

    @Autowired
    private IParkLaneService parkLaneService;

    /**
     * 分页查询车道列表
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult listForParkLane(ParkLaneSelectDto dto) {
        return this.getDataTable(this.parkLaneService.listForParkLane(dto));
    }


    /**
     * 所有车道
     *
     * @return
     */
    @GetMapping("/allList")
    public AjaxResult getAllParkLaneList() {
        return AjaxResult.success(this.parkLaneService.allListForParkLane());
    }

    /**
     * 新增车道
     *
     * @param parkLaneEntity
     * @return
     */
    @Log(title = BusinessTitle.PARK_LANE, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addParkLane(@RequestBody ParkLaneEntity parkLaneEntity) {
        parkLaneEntity.setCreateBy(SecurityUtils.getUsername());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return AjaxResult.error("非管理员不能新增");
        }
        return this.toAjax(this.parkLaneService.addParkLane(parkLaneEntity));
    }

    /**
     * 编辑车道
     *
     * @param parkLaneEntity
     * @return
     */
    @Log(title = BusinessTitle.PARK_LANE, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updateParkLane(@RequestBody ParkLaneEntity parkLaneEntity) {
        parkLaneEntity.setUpdateBy(SecurityUtils.getUsername());
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return AjaxResult.error("非管理员不能编辑");
        }
        return this.toAjax(this.parkLaneService.updateParkLane(parkLaneEntity));
    }

    /**
     * 删除车道
     *
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.PARK_LANE, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delParkLane(@PathVariable Integer[] ids) {
        return this.toAjax(this.parkLaneService.delParkLane(ids));
    }

}
