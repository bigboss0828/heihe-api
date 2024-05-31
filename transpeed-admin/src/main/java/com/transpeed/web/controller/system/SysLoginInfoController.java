package com.transpeed.web.controller.system;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.system.common.dto.logininfo.SysLoginInfoDto;
import com.transpeed.system.service.ISysLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanshuai
 * @title: SysLoginInfoController
 * @description: 登录日志
 * @date 2023/6/16 14:03
 */
@RestController
@RequestMapping("/system/loginInfo")
public class SysLoginInfoController extends BaseController {

    @Autowired
    private ISysLoginInfoService loginInfoService;

    /**
     * 查询登录日志表格数据
     *
     * @param loginInfoDto 查询条件
     * @return 表格数据
     */
    @GetMapping("/listForLoginInfo")
    public AjaxResult listForLoginInfo(SysLoginInfoDto loginInfoDto){
        return this.getDataTable(this.loginInfoService.listForLoginInfo(loginInfoDto));
    }

}
