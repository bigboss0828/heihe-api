package com.transpeed.framework.security.handle;

import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.core.domain.model.LoginUser;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hanshuai
 * @title: LogoutSuccessHandlerImpl
 * @description: 自定义退出处理类
 * @date 2023/6/1 10:28
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            redisCache.deleteObject(UserConstants.SYSTEM_MENU_CACHE + userName);
            // 删除token
            tokenService.delLoginUser(loginUser);
        }
    }

}
