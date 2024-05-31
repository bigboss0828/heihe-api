package com.transpeed.framework.manager.factory;

import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.system.entity.SysLoginInfoEntity;
import com.transpeed.system.entity.SysOperLogEntity;
import com.transpeed.system.service.ISysLoginInfoService;
import com.transpeed.system.service.ISysOperLogService;
import com.transpeed.common.utils.ServletUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.common.utils.spring.SpringUtils;
import eu.bitwalker.useragentutils.UserAgent;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author hanshuai
 * @title: AsyncFactory
 * @description: 异步工厂（产生任务用）
 * @date 2023/6/1 09:31
 */
public class AsyncFactory {

    /**
     * 记录登录信息
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message,
                                             final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        return new TimerTask() {
            @Override
            public void run() {
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SysLoginInfoEntity loginInfo = new SysLoginInfoEntity();
                loginInfo.setUserName(username);
                loginInfo.setLoginBrowser(browser);
                loginInfo.setLoginOs(os);
                loginInfo.setRemark(message);
                // 日志状态
                if (StringUtils.equalsAny(status, UserConstants.LOGIN_SUCCESS, UserConstants.LOGOUT)) {
                    loginInfo.setLoginStatus(SystemConstants.SUCCESS);
                } else if (UserConstants.LOGIN_FAIL.equals(status)) {
                    loginInfo.setLoginStatus(SystemConstants.FAIL);
                }
                loginInfo.setLoginTime(new Date());
                // 插入数据
                SpringUtils.getBean(ISysLoginInfoService.class).insert(loginInfo);
            }
        };
    }

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SysOperLogEntity operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(ISysOperLogService.class).insertOperlog(operLog);
            }
        };
    }

}
