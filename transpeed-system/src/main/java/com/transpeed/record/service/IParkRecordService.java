package com.transpeed.record.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.record.common.ParkRecordSelectDto;
import com.transpeed.record.common.vo.ParkRecordExportVo;
import com.transpeed.record.entity.ParkRecordEntity;

import java.util.List;

/**
 * @title: IParkRecordService
 * @Author zhangwenxiang
 * @Date: 2023/9/19 17:56
 * @Version 1.0
 */

public interface IParkRecordService {

    DataGridView listForParkRecord(ParkRecordSelectDto dto);

    List<ParkRecordExportVo> exportParkRecord(ParkRecordSelectDto dto);

}
