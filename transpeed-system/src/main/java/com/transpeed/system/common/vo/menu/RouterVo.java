package com.transpeed.system.common.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hanshuai
 * @title: RouterVo
 * @description: 路由信息
 * @date 2023/6/1 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVo {

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 路径
     */
    private String path;

    /**
     * 菜单名称（英文）
     */
    private String name;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 配置项
     */
    private Meta meta;

    /**
     * 排序字段
     */
    private Integer orderNum;

    /**
     * 子路由
     */
    private List<RouterVo> children;

}
