package com.transpeed.common.utils;

import com.transpeed.common.core.domain.model.LoginUser;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.common.constant.system.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author hanshuai
 * @title: SecurityUtils
 * @description: 安全服务工具类
 * @date 2023/5/26 14:08
 */
public class SecurityUtils {

    /**
     * 用户ID
     **/
    public static Integer getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new ServiceException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户标记（当前停车场）
     **/
    public static String getCurrentPark() {
        try {
            return getLoginUser().getCurrentPark();
        } catch (Exception e) {
            throw new ServiceException("获取用户标记异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户角色类型
     **/
    public static Integer getUserType() {
        try {
            return getLoginUser().getUserType();
        } catch (Exception e) {
            throw new ServiceException("获取用户类型异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new ServiceException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Integer userId) {
        return userId != null && 1 == userId ? true : false;
    }

}
