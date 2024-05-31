package com.transpeed.common.constant.business.system.user;

/**
 * @author hanshuai
 * @title: UserConstants
 * @description: 用户常量
 * @date 2023/6/1 09:03
 */
public class UserConstants {

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码时效 两分钟
     */
    public final static Long CAPTCHA_EXPIRE_TIME = 120L;

    /**
     * 用户菜单缓存key
     */
    public final static String SYSTEM_MENU_CACHE = "system_menu:";

    /**
     * 用户菜单缓存时间
     */
    public final static Long SYSTEM_MENU_CACHE_EXPIRE_TIME = 30L;

    /**
     * 校验用户重复登录
     */
    public static final String LOGIN_VERSION = "login_version:";

    /**
     * 用户不存在/密码错误
     */
    public static final String USER_LOGIN_USER_PASSWORD_NOT_MATCH = "用户不存在/密码错误";

    /**
     * 登录成功
     */
    public static final String USER_LOGIN_SUCCESS = "登录成功";

    /**
     * 验证码已过期
     */
    public static final String CAPTCHA_OVERDUE = "验证码已过期";

    /**
     * 验证码错误
     */
    public static final String CAPTCHA_NOT_MATCH = "验证码错误";

    /**
     * 重复登录
     */
    public static final String REPEAT_LOGIN = "账号已在别处登录";

    /**
     * 用户最多绑定角色个数
     */
    public static final Integer USER_MOST_ROLE = 3;

}
