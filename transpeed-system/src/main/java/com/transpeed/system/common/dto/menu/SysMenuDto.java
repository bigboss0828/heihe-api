package com.transpeed.system.common.dto.menu;

import com.transpeed.common.constant.business.system.menu.MenuType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hanshuai
 * @title: SysMenuDto
 * @description: 菜单接收前端对象
 * @date 2023/6/7 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuDto {

    /**
     * 主键ID
     */
    private Integer menuId;

    /**
     * 路由 name (对应页面组件 name, 可用作 KeepAlive 缓存标识 && 按钮权限筛选)
     */
    private String menuName;

    /**
     * 父级ID
     */
    @NotNull(message = "父级菜单不能为空")
    private Integer parentId;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 菜单类型（0目录 1菜单 2按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    /**
     * 权限字符
     */
    private String perms;

    /**
     * 路由访问路径
     */
    private String path;

    /**
     * 视图文件路径
     */
    private String component;

    /**
     * 路由标题
     */
    @NotBlank(message = "路由标题不能为空")
    private String title;

    /**
     * 路由外链时填写的访问地址
     */
    private String isLink;

    /**
     * 是否在菜单中隐藏 (通常列表详情页需要隐藏)
     */
    private Integer isHide;

    /**
     * 当前路由是否缓存
     */
    private Integer isKeepAlive;

    /**
     * 菜单是否固定在标签页中 (首页通常是固定项)
     */
    private Integer isAffix;

    /**
     * 菜单是否全屏 (示例：数据大屏页面)
     */
    private Integer isFull;

    /**
     * 图标
     */
    private String icon;

    @AssertTrue(message = "当菜单类型为按钮时，权限字符不能为空")
    public boolean btnValid() {
        if (menuType != null && menuType == MenuType.BTN.getCode()) {
            return perms != null && !perms.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单时，视图路径不能为空")
    public boolean menuComponentValid() {
        if (menuType != null && menuType == MenuType.MENU.getCode()) {
            return component != null && !component.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，路由名称不能为空")
    public boolean menuNameValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode() || menuType == MenuType.CATA.getCode())) {
            return menuName != null && !menuName.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，路由路径不能为空")
    public boolean menuPathValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return path != null && !path.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，菜单图标不能为空")
    public boolean menuIconValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return icon != null && !icon.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，是否隐藏不能为空")
    public boolean menuIsHideValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return isHide != null;
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，是否缓存不能为空")
    public boolean menuIsKeepAliveValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return isKeepAlive != null;
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，是否固定不能为空")
    public boolean menuIsAffixValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return isAffix != null;
        }
        return true;
    }

    @AssertTrue(message = "当菜单类型为菜单或目录时，是否全屏不能为空")
    public boolean menuIsFullValid() {
        if (menuType != null && (menuType == MenuType.MENU.getCode()|| menuType == MenuType.CATA.getCode())) {
            return isFull != null;
        }
        return true;
    }

}
