package com.transpeed.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.transpeed.common.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hanshuai
 * @title: SysMenu
 * @description: 菜单实体
 * @date 2023/6/1 14:29
 */
@TableName(value = "sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    /**
     * 路由 name (对应页面组件 name, 可用作 KeepAlive 缓存标识 && 按钮权限筛选)
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 父级ID
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 菜单类型（0目录 1菜单 2按钮）
     */
    @TableField(value = "menu_type")
    private Integer menuType;

    /**
     * 权限字符
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 路由访问路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 视图文件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 路由标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 路由外链时填写的访问地址
     */
    @TableField(value = "is_link")
    private String isLink;

    /**
     * 是否在菜单中隐藏 (通常列表详情页需要隐藏)
     */
    @TableField(value = "is_hide")
    private Integer isHide;

    /**
     * 当前路由是否缓存
     */
    @TableField(value = "is_keep_alive")
    private Integer isKeepAlive;

    /**
     * 菜单是否固定在标签页中 (首页通常是固定项)
     */
    @TableField(value = "is_affix")
    private Integer isAffix;

    /**
     * 菜单是否全屏 (示例：数据大屏页面)
     */
    @TableField(value = "is_full")
    private Integer isFull;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 菜单状态（0：正常 1：停用 ）
     */
    @TableField(value = "menu_status")
    private Integer menuStatus;

}
