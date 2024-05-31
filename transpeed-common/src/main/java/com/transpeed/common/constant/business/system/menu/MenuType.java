package com.transpeed.common.constant.business.system.menu;

/**
 * @author hanshuai
 * @title: MenuType
 * @description: 菜单类型枚举
 * @date 2023/6/1 15:54
 */
public enum MenuType {

    CATA(0, "目录"), MENU(1, "菜单"), BTN(2, "按钮");

    private final Integer code;
    private final String info;

    MenuType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
