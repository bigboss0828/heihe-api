package com.transpeed.system.common.vo.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: MetaVo
 * @description: 路由信息
 * @date 2023/6/1 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    /**
     * 图标
     */
    private String icon;

    /**
     * 页面名称（中文）
     */
    private String title;

    /**
     * 是否外链
     */
    private String isLink;

    /**
     * 是否显示（默认：false）
     */
    private Integer isHide;

    /**
     * 是否内嵌（默认：false）
     */
    private Integer isFull;

    /**
     * 是否固定（默认：true）
     */
    private Integer isAffix;

    /**
     * 是否缓存（默认：true）
     */
    private Integer isKeepAlive;

}
