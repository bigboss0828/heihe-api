package com.transpeed.common.constant.system;

/**
 * @author hanshuai
 * @title: SsytemConstants
 * @description: 系统常量信息
 * @date 2023/5/26 10:55
 */
public class SystemConstants {

    /**
     * SQL取一条
     */
    public static final String SQL_LIMIT = "limit 1";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final Integer SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final Integer FAIL = 1;

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "transpeed";

    /**
     * token有效期
     */
    public final static Long EXPIRE_TIME = 30L;

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 时间格式化
     */
    public static final String TIME_ZONE = "GMT+8";

    /**
     * 系统通用正常
     */
    public static final Integer SYSTEM_STATUS_NORMAL = 0;

    /**
     * 系统通用停用
     */
    public static final Integer SYSTEM_STATUS_DISABLE = 1;

    /**
     * 校验是否重复：不重复
     */
    public final static String UNIQUE = "0";

    /**
     * 校验是否重复：重复
     */
    public final static String NOT_UNIQUE = "1";

}
