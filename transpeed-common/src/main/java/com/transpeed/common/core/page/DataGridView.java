package com.transpeed.common.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author hanshuai
 * @title: DataGridView
 * @description: 表格数据传输对象
 * @date 2023/5/26 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView implements Serializable {

    /**
     * 当前页数
     */
    private Long pageNum;

    /**
     * 数据条数
     */
    private Long pageSize;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据集合
     */
    private List<?> data;

}
