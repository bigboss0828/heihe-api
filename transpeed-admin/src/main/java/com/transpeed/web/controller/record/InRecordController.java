package com.transpeed.web.controller.record;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.poi.ExcelUtil;
import com.transpeed.record.common.InRecordSelectDto;
import com.transpeed.record.common.ParkRecordSelectDto;
import com.transpeed.record.common.vo.InRecordExportVo;
import com.transpeed.record.common.vo.ParkRecordExportVo;
import com.transpeed.record.service.IInRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title: InRecordController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:57
 * @Version 1.0
 */
@RestController
@RequestMapping("/record/in")
public class InRecordController extends BaseController {

    @Autowired
    private IInRecordService iInRecordService;

    @GetMapping("/list")
    public AjaxResult getInRecordList(InRecordSelectDto dto) {
        return this.getDataTable(this.iInRecordService.listForInRecord(dto));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody InRecordSelectDto dto) {
        List<InRecordExportVo> inRecordExportVos = this.iInRecordService.exportInRecord(dto);
        ExcelUtil<InRecordExportVo> util = new ExcelUtil<>(InRecordExportVo.class);
        util.exportExcel(response, inRecordExportVos, "入场纪录");
    }

}
