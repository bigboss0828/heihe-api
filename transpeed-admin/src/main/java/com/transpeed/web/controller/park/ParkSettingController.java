package com.transpeed.web.controller.park;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.park.entity.ParkSettingEntity;
import com.transpeed.park.service.IParkSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: ParkSettingController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/20 9:13
 * @Version 1.0
 */

@RestController
@RequestMapping("/park/setting")
public class ParkSettingController extends BaseController {

    @Autowired
    private IParkSettingService parkSettingService;

    @GetMapping("/{parkCode}")
    public AjaxResult getSettingByParkCode(@PathVariable String parkCode) {
        ParkSettingEntity parkSettingEntity = this.parkSettingService.settingByParkCode(parkCode);
        if (parkSettingEntity == null) {
//            ParkSettingEntity parkSettingNew = new ParkSettingEntity();
//            parkSettingNew.setParkCode(parkCode);
            return AjaxResult.error("查询停车场设置失败!");
        }
        return AjaxResult.success("查询成功!", parkSettingEntity);
    }

    /**
     * 新增设置
     *
     * @param parkSettingEntity
     * @return
     */
    @Log(title = BusinessTitle.PARK_SETTING, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addSetting(@RequestBody ParkSettingEntity parkSettingEntity) {
        parkSettingEntity.setCreateBy(SecurityUtils.getUsername());
        return this.toAjax(this.parkSettingService.addSetting(parkSettingEntity));
    }

    /**
     * 更新停车场设置
     *
     * @param parkSettingEntity
     * @return
     */
    @Log(title = BusinessTitle.PARK_SETTING, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updateSetting(@RequestBody ParkSettingEntity parkSettingEntity) {
        parkSettingEntity.setUpdateBy(SecurityUtils.getUsername());
        return this.toAjax(this.parkSettingService.updateSetting(parkSettingEntity));
    }

}
