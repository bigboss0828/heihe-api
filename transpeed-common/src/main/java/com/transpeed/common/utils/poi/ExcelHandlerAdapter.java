package com.transpeed.common.utils.poi;

/**
 * @author hanshuai
 * @title: ExcelHandlerAdapter
 * @description: Excel数据格式处理适配器
 * @date 2022/2/21 14:04
 */
public interface ExcelHandlerAdapter {

    /**
     * 格式化
     * @param value 单元格数据值
     * @param args excel注解args参数组
     * @return 处理后的值
     */
    Object format(Object value, String[] args);

}
