package com.transpeed.record.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.record.common.InRecordSelectDto;
import com.transpeed.record.common.vo.InRecordExportVo;

import java.util.List;

/**
 * @title: IInRecordService
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:50
 * @Version 1.0
 */

public interface IInRecordService {

    DataGridView listForInRecord(InRecordSelectDto dto);

    List<InRecordExportVo> exportInRecord(InRecordSelectDto dto);

}
