package com.transpeed.web.controller.system;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.core.domain.entity.SysRoleEntity;
import com.transpeed.system.common.dto.role.SysRoleDto;
import com.transpeed.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hanshuai
 * @title: SysRoleController
 * @description: 角色管理
 * @date 2023/6/9 15:22
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 查询角色表格数据
     *
     * @param roleDto 查询条件
     * @return 表格数据
     */
    @GetMapping("/listForRole")
    public AjaxResult listForRole(SysRoleDto roleDto) {
        return this.getDataTable(this.roleService.listForRole(roleDto));
    }

    /**
     * 查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    @GetMapping("/{roleId}")
    public AjaxResult getInfoRole(@PathVariable Integer roleId) {
        return AjaxResult.success(this.roleService.getInfoRole(roleId));
    }

    /**
     * 添加角色信息
     *
     * @param roleDto 角色实体
     * @return 添加结果
     */
    @PreAuthorize("@ss.hasPermi('sys:role:add')")
    @Log(title = BusinessTitle.SYSTEM_ROLE, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addRole(@Validated @RequestBody SysRoleDto roleDto) {
        if (SystemConstants.NOT_UNIQUE.equals(this.roleService.checkRoleNameUnique(roleDto))) {
            return AjaxResult.error("新增角色'" + roleDto.getRoleName() + "'失败，角色名称已经存在！");
        }
        return this.toAjax(this.roleService.addRole(roleDto));
    }

    /**
     * 修改角色信息
     *
     * @param roleDto 角色实体
     * @return 修改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:role:update')")
    @Log(title = BusinessTitle.SYSTEM_ROLE, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateRole(@Validated @RequestBody SysRoleDto roleDto) {
        if (SystemConstants.NOT_UNIQUE.equals(this.roleService.checkRoleNameUnique(roleDto))) {
            return AjaxResult.error("修改角色'" + roleDto.getRoleName() + "'失败，角色名称已经存在！");
        }
        return this.toAjax(this.roleService.updateRole(roleDto));
    }

    /**
     * 删除角色数据
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    @PreAuthorize("@ss.hasPermi('sys:role:del')")
    @Log(title = BusinessTitle.SYSTEM_ROLE, businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{roleId}")
    public AjaxResult delRole(@PathVariable Integer roleId) {
        // 校验当前角色是否已经在使用
        if (this.roleService.roleHasUser(roleId) > 0) {
            return AjaxResult.error("删除角色失败，当前角色有绑定用户，请先进行解绑！");
        }
        return this.toAjax(this.roleService.delRole(roleId));
    }

    /**
     * 更改角色状态
     *
     * @param roleDto 角色实体
     * @return 更改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:role:changeStatus')")
    @Log(title = BusinessTitle.SYSTEM_ROLE, businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatusRole")
    public AjaxResult changeStatusRole(@RequestBody SysRoleDto roleDto) {
        // 校验当前角色是否已经在使用
        if (this.roleService.roleHasUser(roleDto.getRoleId()) > 0) {
            return AjaxResult.error("停用角色失败，当前角色有绑定用户，请先进行解绑！");
        }
        boolean updateFlag = this.roleService.lambdaUpdate()
                .set(SysRoleEntity::getRoleStatus, roleDto.getRoleStatus())
                .eq(SysRoleEntity::getRoleId, roleDto.getRoleId())
                .update();
        return updateFlag ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 获取所有可用角色集合
     *
     * @return 角色集合
     */
    @GetMapping("/getSelectRole")
    public AjaxResult getSelectRole() {
        return AjaxResult.success(this.roleService.getSelectRole());
    }

}
