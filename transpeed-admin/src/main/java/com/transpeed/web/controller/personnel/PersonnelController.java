package com.transpeed.web.controller.personnel;

import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.poi.ExcelUtil;
import com.transpeed.personnel.common.dto.PersonnelImportDto;
import com.transpeed.personnel.common.dto.PersonnelSelectDto;
import com.transpeed.personnel.common.dto.PersonnelUIDto;
import com.transpeed.personnel.entity.PersonnelEntity;
import com.transpeed.personnel.service.IPersonnelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title: PersonnelController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 9:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/personnel")
public class PersonnelController extends BaseController {

    @Autowired
    private IPersonnelService personnelService;

    /**
     * 分页查询人员表
     *
     * @param dto
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getListForPersonnel(PersonnelSelectDto dto) {
        return this.getDataTable(this.personnelService.listForPersonnel(dto));
    }

    /**
     * 新增人员
     *
     * @param dto
     * @return
     */
    @Log(title = BusinessTitle.PERSON_INFO, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addPersonnel(@Validated @RequestBody PersonnelUIDto dto) {
        PersonnelEntity personnelEntity = new PersonnelEntity();
        BeanUtils.copyProperties(dto, personnelEntity);
        personnelEntity.setCreateBy(SecurityUtils.getUsername());
        if (SystemConstants.NOT_UNIQUE.equals(this.personnelService.checkIcNoUnique(personnelEntity))) {
            return AjaxResult.error("新增人员'" + dto.getOwnerName() + "'失败，IC卡号已经存在！");
        }
        if (SystemConstants.NOT_UNIQUE.equals(this.personnelService.checkIdCardUnique(personnelEntity))) {
            return AjaxResult.error("新增人员'" + dto.getOwnerName() + "'失败，身份证号已经存在！");
        }
        return this.toAjax(this.personnelService.addPersonnel(personnelEntity));
    }

    /**
     * 编辑人员
     *
     * @param personnelUIDto
     * @return
     */
    @Log(title = BusinessTitle.PERSON_INFO, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updatePersonnel(@RequestBody PersonnelUIDto personnelUIDto) {
        PersonnelEntity personnelEntity = new PersonnelEntity();
        BeanUtils.copyProperties(personnelUIDto, personnelEntity);
        personnelEntity.setUpdateBy(SecurityUtils.getUsername());
        if (SystemConstants.NOT_UNIQUE.equals(this.personnelService.checkIcNoUnique(personnelEntity))) {
            return AjaxResult.error("编辑人员'" + personnelUIDto.getOwnerName() + "'失败，IC卡号已经存在！");
        }
        if (SystemConstants.NOT_UNIQUE.equals(this.personnelService.checkIdCardUnique(personnelEntity))) {
            return AjaxResult.error("编辑人员'" + personnelUIDto.getOwnerName() + "'失败，身份证号已经存在！");
        }
        return this.toAjax(this.personnelService.updatePersonnel(personnelEntity));
    }

    /**
     * 删除人员
     *
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.PERSON_INFO, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delPersonnel(@PathVariable Integer[] ids) {
        return this.toAjax(this.personnelService.delPersonnel(ids));
    }

    @PostMapping("/import")
    public AjaxResult importPersonnel(MultipartFile file, PersonnelImportDto dto) throws Exception {
        ExcelUtil<PersonnelEntity> util = new ExcelUtil<>(PersonnelEntity.class);
        List<PersonnelEntity> whites = util.importExcel(file.getInputStream());
        String message = this.personnelService.importPersonnel(whites, dto);
        return AjaxResult.success(message);
    }

    @PostMapping("/exportTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<PersonnelEntity> util = new ExcelUtil<PersonnelEntity>(PersonnelEntity.class);
        util.importTemplateExcel(response, "人员模板");
    }

}
