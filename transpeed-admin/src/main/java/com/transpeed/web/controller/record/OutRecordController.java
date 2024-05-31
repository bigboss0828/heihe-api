package com.transpeed.web.controller.record;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.poi.ExcelUtil;
import com.transpeed.record.common.InRecordSelectDto;
import com.transpeed.record.common.OutRecordSelectDto;
import com.transpeed.record.common.vo.InRecordExportVo;
import com.transpeed.record.common.vo.OutRecordExportVo;
import com.transpeed.record.service.IOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title: OutRecordController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:35
 * @Version 1.0
 */
@RestController
@RequestMapping("/record/out")
public class OutRecordController extends BaseController {

    @Autowired
    private IOutRecordService outRecordService;

    @GetMapping("/list")
    public AjaxResult getOutRecordList(OutRecordSelectDto dto) {
        return this.getDataTable(this.outRecordService.listForOutRecord(dto));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody OutRecordSelectDto dto) {
        List<OutRecordExportVo> outRecordExportVos = this.outRecordService.exportOutRecord(dto);
        ExcelUtil<OutRecordExportVo> util = new ExcelUtil<>(OutRecordExportVo.class);
        util.exportExcel(response, outRecordExportVos, "出场纪录");

    }

}
