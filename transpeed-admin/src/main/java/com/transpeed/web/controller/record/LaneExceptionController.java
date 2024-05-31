package com.transpeed.web.controller.record;

import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.controller.BaseController;
import com.transpeed.record.common.LaneExcepSelectDto;
import com.transpeed.record.service.ILaneExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: LaneExceptionController
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/record/laneExcept")
public class LaneExceptionController extends BaseController {

    @Autowired
    private ILaneExceptionService laneExceptionService;

    @GetMapping("/list")
    public AjaxResult listForLaneExcept(LaneExcepSelectDto dto) {
        return this.getDataTable(this.laneExceptionService.listForLaneException(dto));
    }

}
