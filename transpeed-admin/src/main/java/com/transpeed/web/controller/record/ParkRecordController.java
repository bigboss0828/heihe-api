package com.transpeed.web.controller.record;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.common.utils.poi.ExcelUtil;
import com.transpeed.record.common.ParkRecordSelectDto;
import com.transpeed.record.common.vo.ParkRecordExportVo;
import com.transpeed.record.service.IParkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @title: ParkRecordController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 18:00
 * @Version 1.0
 */
@RestController
@RequestMapping("/park/record")
public class ParkRecordController extends BaseController {

    @Autowired
    private IParkRecordService parkRecordService;

    @GetMapping("/list")
    public AjaxResult getParkRecordList(ParkRecordSelectDto dto) {
        return this.getDataTable(this.parkRecordService.listForParkRecord(dto));
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody ParkRecordSelectDto dto) {
        List<ParkRecordExportVo> parkRecordExportVos = this.parkRecordService.exportParkRecord(dto);
        ExcelUtil<ParkRecordExportVo> util = new ExcelUtil<>(ParkRecordExportVo.class);
        util.exportExcel(response, parkRecordExportVos, "停车场纪录");
    }

}
