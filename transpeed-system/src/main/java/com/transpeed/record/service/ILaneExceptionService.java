package com.transpeed.record.service;

import com.transpeed.common.core.page.DataGridView;
import com.transpeed.record.common.LaneExcepSelectDto;

/**
 * @title: ILaneExceptionService
 * @Author zhangwenxiang
 * @Date: 2023/9/19 16:01
 * @Version 1.0
 */

public interface ILaneExceptionService {

    DataGridView listForLaneException(LaneExcepSelectDto dto);

}
