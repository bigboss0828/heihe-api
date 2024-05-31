package com.transpeed.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.transpeed.common.constant.system.HttpStatus;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.utils.ServletUtils;
import com.transpeed.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author hanshuai
 * @title: AuthenticationEntryPointImpl
 * @description: 认证失败处理类
 * @date 2023/6/1 10:27
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }

}
