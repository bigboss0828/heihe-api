package com.transpeed.record.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.record.common.OutRecordSelectDto;
import com.transpeed.record.common.vo.OutRecordExportVo;

import java.util.List;

/**
 * @title: IOutRecordService
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:30
 * @Version 1.0
 */

public interface IOutRecordService {

    DataGridView listForOutRecord(OutRecordSelectDto dto);

    List<OutRecordExportVo> exportOutRecord(OutRecordSelectDto dto);

}
