package com.transpeed.web.controller.personnel;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.personnel.entity.PersonnelLevelEntity;
import com.transpeed.personnel.service.IPersonnelLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: PersonnelLevelController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 10:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/personnel/level")
public class PersonnelLevelController extends BaseController {

    @Autowired
    private IPersonnelLevelService levelService;

    /**
     * 查询所有人员登记列表
     *
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getListForPersonnelLevel() {
        return AjaxResult.success(this.levelService.listForPersonnelLevel());
    }

    /**
     * 新增人员等级
     *
     * @param levelEntity
     * @return
     */
    @Log(title = BusinessTitle.PERSON_LEVEL, businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult addPersonnelLevel(@RequestBody PersonnelLevelEntity levelEntity) {
        levelEntity.setCreateBy(SecurityUtils.getUsername());
        //  PersonnelLevelEntity levelByKey = this.levelService.getLevelByKey(levelEntity.getTypeKey());
        //  if (levelByKey != null) {
        //      return AjaxResult.error("新增失败，等级编码：" + levelEntity.getTypeKey() + "已存在");
        //  }
        Integer maxLevel = this.levelService.getMaxLevel();
        levelEntity.setTypeKey(String.valueOf(maxLevel + 1));
        return this.toAjax(this.levelService.addPersonnelLevel(levelEntity));
    }

    /**
     * 更新人员等级
     *
     * @param levelEntity
     * @return
     */
    @Log(title = BusinessTitle.PERSON_LEVEL, businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updatePersonnelLevel(@RequestBody PersonnelLevelEntity levelEntity) {
        levelEntity.setUpdateBy(SecurityUtils.getUsername());
        //  PersonnelLevelEntity levelByKey = this.levelService.getLevelByKey(levelEntity.getTypeKey());
        //  if (levelByKey != null) {
        //      return AjaxResult.error("更新失败，等级编码：" + levelEntity.getTypeKey() + "已存在");
        //  }
        return this.toAjax(this.levelService.updatePersonnelLevel(levelEntity));
    }

    /**
     * 删除人员等级
     * @param ids
     * @return
     */
    @Log(title = BusinessTitle.PERSON_LEVEL, businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delPersonnelLevel(@PathVariable Integer[] ids) {
        return this.toAjax(this.levelService.delPersonnelLevel(ids));
    }

}
