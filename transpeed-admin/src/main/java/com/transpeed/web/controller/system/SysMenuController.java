package com.transpeed.web.controller.system;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.menu.MenuConstants;
import com.transpeed.common.constant.business.system.menu.MenuType;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.system.common.dto.menu.SysMenuDto;
import com.transpeed.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hanshuai
 * @title: SysMenuController
 * @description: 菜单管理
 * @date 2023/6/7 10:04
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 查询菜单表格数据
     *
     * @param menuDto 查询条件
     * @return 表格数据
     */
    @GetMapping("/listForMenu")
    public AjaxResult listForMenu(SysMenuDto menuDto) {
        return AjaxResult.success(this.menuService.listForMenu(menuDto));
    }

    /**
     * 查询菜单数据信息
     *
     * @param menuId 菜单ID
     * @return 菜单数据
     */
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfoMenu(@PathVariable Integer menuId) {
        return AjaxResult.success(this.menuService.getInfoMenu(menuId));
    }

    /**
     * 添加菜单信息
     *
     * @param menuDto 菜单实体
     * @return 添加结果
     */
    @PreAuthorize("@ss.hasPermi('sys:menu:add')")
    @Log(title = BusinessTitle.SYSTEM_MENU, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addMenu(@Validated @RequestBody SysMenuDto menuDto) {
        if (SystemConstants.NOT_UNIQUE.equals(this.menuService.checkMenuTitleUnique(menuDto))) {
            return AjaxResult.error("新增菜单'" + menuDto.getTitle() + "'失败，菜单名称已经存在！");
        }
        if (MenuType.BTN.equals(menuDto.getMenuType()) && MenuConstants.TOP_LEVEL_MENU.equals(menuDto.getParentId())) {
            return AjaxResult.error("新增菜单'" + menuDto.getTitle() + "'失败，按钮不能为顶级菜单！");
        }
        return this.toAjax(this.menuService.addMenu(menuDto));
    }

    /**
     * 修改菜单信息
     *
     * @param menuDto 菜单实体
     * @return 修改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:menu:update')")
    @Log(title = BusinessTitle.SYSTEM_MENU, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateMenu(@Validated @RequestBody SysMenuDto menuDto) {
        if (SystemConstants.NOT_UNIQUE.equals(this.menuService.checkMenuTitleUnique(menuDto))) {
            return AjaxResult.error("修改菜单'" + menuDto.getMenuName() + "'失败，菜单名称已经存在");
        }
        return this.toAjax(this.menuService.updateMenu(menuDto));
    }

    /**
     * 删除菜单数据
     *
     * @param menuId 菜单ID
     * @return 删除结果
     */
    @PreAuthorize("@ss.hasPermi('sys:menu:del')")
    @Log(title = BusinessTitle.SYSTEM_MENU, businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{menuId}")
    public AjaxResult delMenu(@PathVariable Integer menuId) {
        return this.toAjax(this.menuService.delMenu(menuId));
    }

    /**
     * 查询所有菜单 树形结构（选择父级、绑定角色）
     *
     * @return 树形结构菜单
     */
    @GetMapping("/getSelectMenu")
    public AjaxResult getSelectMenu() {
        return AjaxResult.success(this.menuService.getSelectMenu());
    }

}
