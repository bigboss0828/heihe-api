package com.transpeed.framework.security.filter;

import com.alibaba.fastjson2.JSON;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.constant.system.HttpStatus;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.domain.model.LoginUser;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.ServletUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hanshuai
 * @title: JwtAuthenticationTokenFilter
 * @description:
 * @date 2023/6/1 10:22
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            // 验证用户重复登录 先校验版本 挤下线
            boolean isSuccess = tokenService.checkVersion(loginUser);
            if (!isSuccess){
                ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.CONFLICT, UserConstants.REPEAT_LOGIN)));
                return;
            }
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}
