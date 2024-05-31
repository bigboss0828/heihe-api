package com.transpeed.web.controller.system;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.system.common.dto.operlog.SysOperLogDto;
import com.transpeed.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanshuai
 * @title: SysOperLogController
 * @description: 操作日志
 * @date 2023/6/19 16:44
 */
@RestController
@RequestMapping("/system/operlog")
public class SysOperLogController extends BaseController {

    @Autowired
    private ISysOperLogService operLogService;

    /**
     * 查询操作日志表格数据
     *
     * @param operLogDto 查询条件
     * @return 表格数据
     */
    @GetMapping("/listForOperLog")
    public AjaxResult listForOperLog(SysOperLogDto operLogDto) {
        return this.getDataTable(this.operLogService.listForOperLog(operLogDto));
    }

}
