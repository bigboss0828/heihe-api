package com.transpeed.system.common.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hanshuai
 * @title: MenuSelectVo
 * @description: 树形结构菜单
 * @date 2023/6/8 09:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSelectVo {

    /**
     * 值
     */
    private Integer value;

    /**
     * 标签
     */
    private String label;

    /**
     * 字典类型
     */
    private Integer menuType;

    /**
     * 排序字段
     */
    private Integer orderNum;

    /**
     * 子元素
     */
    private List<MenuSelectVo> children;

}
