package com.transpeed.common.utils.business;

import com.transpeed.common.constant.business.system.user.UserType;

/**
 * @author hanshuai
 * @title: SysUtils
 * @description: 系统工具类 （查询数据时区分各个管理员之间的数据权限）
 * @date 2023/6/14 17:49
 */
public class SysUtils {

    /**
     * 判断当前用户是否为超级管理员
     *
     * @param userType 用户类型
     * @return 是否为超级管理员
     */
    public static boolean isAdmin(Integer userType) {
        if (userType != null && UserType.ADMIN.getCode().equals(userType)) {
            return true;
        }
        return false;
    }

    public static boolean isPark() {
        return false;
    }

    public static boolean isCompany() {
        return false;
    }

}
