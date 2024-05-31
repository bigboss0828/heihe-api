package com.transpeed.web.controller.car;

import com.transpeed.car.common.dto.WhiteSelectDto;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.car.service.IWhiteService;
import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title: WhiteController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 19:17
 * @Version 1.0
 */
@RestController
@RequestMapping("/car/white")
public class WhiteController extends BaseController {

    @Autowired
    private IWhiteService whiteService;

    /**
     * 分页查询白名单车辆
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult listForWhite(WhiteSelectDto dto) {
        return this.getDataTable(this.whiteService.listForWhite(dto));
    }

    /**
     * 新增白名单车辆
     *
     * @param whiteEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addWhiteCar(@RequestBody WhiteEntity whiteEntity) {
        if (SystemConstants.NOT_UNIQUE.equals(this.whiteService.checkPlateUnique(whiteEntity))) {
            return AjaxResult.error("新增失败，车牌或者OBUID已存在");
        }
        whiteEntity.setOverdue("0");
        whiteEntity.setLogicDel("0");
        whiteEntity.setCreateBy(SecurityUtils.getUsername());
        return this.toAjax(this.whiteService.addWhiteCar(whiteEntity));
    }

    /**
     * 修改白名单车辆
     *
     * @param whiteEntity
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updateWhiteCar(@RequestBody WhiteEntity whiteEntity) {
        if (SystemConstants.NOT_UNIQUE.equals(this.whiteService.checkPlateUnique(whiteEntity))) {
            return AjaxResult.error("新增失败，车牌或者OBUID已存在");
        }
        whiteEntity.setUpdateBy(SecurityUtils.getUsername());
        return this.toAjax(this.whiteService.updateWhiteCar(whiteEntity));
    }

    /**
     * 删除白名单车辆
     *
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.CAR_WHITE, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delWhiteCar(@PathVariable Integer[] ids) {
        return this.toAjax(this.whiteService.delWhiteCar(ids));
    }

    @PostMapping("/import")
    public AjaxResult importWhite(MultipartFile file, String whiteType, String authLane) throws Exception {
        ExcelUtil<WhiteEntity> util = new ExcelUtil<>(WhiteEntity.class);
        List<WhiteEntity> whites = util.importExcel(file.getInputStream());
        String message = this.whiteService.importWhite(whites, whiteType, authLane);
        return AjaxResult.success(message);
    }

    /**
     * 导出Excel人员模板
     *
     * @param response excel
     */
    @PostMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        ExcelUtil<WhiteEntity> util = new ExcelUtil<WhiteEntity>(WhiteEntity.class);
        util.importTemplateExcel(response, "白名单模板");
    }

}
