package com.transpeed.web.controller.system;

import com.transpeed.common.annotation.Log;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.business.system.BusinessTitle;
import com.transpeed.common.constant.system.BusinessType;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import com.transpeed.system.common.dto.user.SysUserDto;
import com.transpeed.system.common.dto.user.SysUserUpdatePasswordDto;
import com.transpeed.system.service.ISysUserService;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author hanshuai
 * @title: SysUserController
 * @description: 用户管理
 * @date 2023/6/9 15:21
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 查询用户表格数据
     *
     * @param userDto 查询条件
     * @return 表格数据
     */
    @GetMapping("/listForUser")
    public AjaxResult listForUser(SysUserDto userDto) {
        return this.getDataTable(this.userService.listForUser(userDto));
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    public AjaxResult getInfoUser(@PathVariable Integer userId) {
        return AjaxResult.success(this.userService.getInfoUser(userId));
    }

    /**
     * 添加用户信息
     *
     * @param userDto 用户实体
     * @return 添加结果
     */
    @PreAuthorize("@ss.hasPermi('sys:user:add')")
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addUser(@Validated @RequestBody SysUserDto userDto) {
        // TODO: 2023/6/14 写完停车场之后加上授权停车场
        if (SystemConstants.NOT_UNIQUE.equals(this.userService.checkUserNameUnique(userDto))) {
            return AjaxResult.error("新增用户'" + userDto.getUserName() + "'失败，用户名称已经存在！");
        }
        if (StringUtils.isEmpty(userDto.getPassword())) {
            return AjaxResult.error("新增用户'" + userDto.getUserName() + "'失败，请先填写用户密码！");
        }
        if (userDto.getRoleIdList().size() > UserConstants.USER_MOST_ROLE) {
            return AjaxResult.error("新增用户'" + userDto.getUserName() + "'失败，每个用户最多绑定" + UserConstants.USER_MOST_ROLE + "个角色！");
        }
        return this.toAjax(this.userService.addUser(userDto));
    }

    /**
     * 修改用户信息
     *
     * @param userDto 用户实体
     * @return 修改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:user:update')")
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateUser(@Validated @RequestBody SysUserDto userDto) {

        if (SystemConstants.NOT_UNIQUE.equals(this.userService.checkUserNameUnique(userDto))) {
            return AjaxResult.error("修改用户'" + userDto.getUserName() + "'失败，用户名称已经存在！");
        }
        if (userDto.getRoleIdList().size() > UserConstants.USER_MOST_ROLE) {
            return AjaxResult.error("新增用户'" + userDto.getUserName() + "'失败，每个用户最多绑定" + UserConstants.USER_MOST_ROLE + "个角色！");
        }
        return this.toAjax(this.userService.updateUser(userDto));
    }

    /**
     * 删除用户数据
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @PreAuthorize("@ss.hasPermi('sys:user:del')")
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{userId}")
    public AjaxResult delUser(@PathVariable Integer userId) {
        if (SecurityUtils.getUserId().equals(userId)) {
            return AjaxResult.error("当前登录用户不可以删除！");
        }
        return this.toAjax(this.userService.delUser(userId));
    }

    /**
     * 更改用户状态
     *
     * @param userDto 用户实体
     * @return 更改结果
     */
    @PreAuthorize("@ss.hasPermi('sys:user:changeStatus')")
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatusUser")
    public AjaxResult changeStatusUser(@RequestBody SysUserDto userDto) {
        if (SecurityUtils.getUserId().equals(userDto.getUserId())) {
            return AjaxResult.error("当前登录用户不可以更改状态！");
        }
        boolean updateFlag = this.userService.lambdaUpdate()
                // 用户状态
                .set(SysUserEntity::getUserStatus, userDto.getUserStatus())
                // 修改人
                .set(SysUserEntity::getUpdateBy, SecurityUtils.getUsername())
                .eq(SysUserEntity::getUserId, userDto.getUserId())
                .update();
        return updateFlag ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 重置用户密码
     *
     * @param userDto 用户实体
     * @return 重置结果
     */
    @PreAuthorize("@ss.hasPermi('sys:user:resetPassword')")
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.INSERT)
    @PutMapping("/resetPassword")
    public AjaxResult resetPassword(@RequestBody SysUserDto userDto) {
        // TODO: 2023/6/14 此处用户重置密码暂时写死 未来在车场后台配置内进行配置
        boolean updateFlag = this.userService.lambdaUpdate()
                // 用户密码
                .set(SysUserEntity::getPassword, SecurityUtils.encryptPassword("123456"))
                // 修改人
                .set(SysUserEntity::getUpdateBy, SecurityUtils.getUsername())
                .eq(SysUserEntity::getUserId, userDto.getUserId())
                .update();
        return updateFlag ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 用户修改密码
     *
     * @param userDto 用户实体
     * @return 修改结果
     */
    @Log(title = BusinessTitle.SYSTEM_USER, businessType = BusinessType.INSERT)
    @PutMapping("/updatePassword")
    public AjaxResult updatePassword(@RequestBody SysUserUpdatePasswordDto userDto) {
        SysUserEntity user = this.userService.getInfoUser(userDto.getUserId());
        if (user == null) {
            return AjaxResult.error("暂未查到用户信息，请重新登录后重试！");
        }
        boolean flag = SecurityUtils.matchesPassword(userDto.getOldPassword(), user.getPassword());
        if (!flag) {
            return AjaxResult.error("原密码错误，请检查后重试！");
        } else {
            boolean updateFlag = this.userService.lambdaUpdate()
                    // 用户密码
                    .set(SysUserEntity::getPassword, SecurityUtils.encryptPassword(userDto.getNewPassword()))
                    // 修改人
                    .set(SysUserEntity::getUpdateBy, SecurityUtils.getUsername())
                    .eq(SysUserEntity::getUserId, userDto.getUserId())
                    .update();
            return updateFlag ? AjaxResult.success() : AjaxResult.error();
        }
    }

}
