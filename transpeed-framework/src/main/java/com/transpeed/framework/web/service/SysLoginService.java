package com.transpeed.framework.web.service;

import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import com.transpeed.common.core.domain.model.LoginUser;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.framework.manager.AsyncManager;
import com.transpeed.framework.manager.factory.AsyncFactory;
import com.transpeed.system.service.ISysUserService;
import com.transpeed.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hanshuai
 * @title: SysLoginService
 * @description: 登录校验方法
 * @date 2023/5/26 14:22
 */
@Component
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public LoginUser login(String username, String password, String code, String uuid) {
        // 校验验证码
        validateCaptcha(username, code, uuid);
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername 验证当前用户有效性
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // 用户不存在或密码不匹配 写日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, UserConstants.LOGIN_FAIL, UserConstants.USER_LOGIN_USER_PASSWORD_NOT_MATCH));
                throw new ServiceException(UserConstants.USER_LOGIN_USER_PASSWORD_NOT_MATCH);
            } else {
                // 未知异常 写日志
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, UserConstants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        // 登录成功 写日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, UserConstants.LOGIN_SUCCESS, UserConstants.USER_LOGIN_SUCCESS));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 更改用户登录信息（最后登录时间）
        this.recordLoginInfo(loginUser.getUserId());
        // 生成token
        loginUser.setToken(this.tokenService.createToken(loginUser));
        return loginUser;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = UserConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, UserConstants.LOGIN_FAIL, UserConstants.CAPTCHA_OVERDUE));
            throw new ServiceException(UserConstants.CAPTCHA_OVERDUE);
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, UserConstants.LOGIN_FAIL, UserConstants.CAPTCHA_NOT_MATCH));
            throw new ServiceException(UserConstants.CAPTCHA_NOT_MATCH);
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Integer userId) {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUserId(userId);
        sysUser.setLastLoginTime(DateUtils.getNowDate());
        this.userService.updateById(sysUser);
    }

}
