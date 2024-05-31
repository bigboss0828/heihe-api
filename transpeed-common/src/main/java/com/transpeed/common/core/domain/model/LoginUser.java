package com.transpeed.common.core.domain.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.transpeed.common.core.domain.entity.SysUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author hanshuai
 * @title: LoginUser
 * @description: 登陆用户身份权限
 * @date 2023/5/26 13:54
 */
public class LoginUser implements UserDetails {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 当前停车场
     */
    private String currentPark;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 用户信息
     */
    private SysUserEntity user;

    /**
     * 用户类型（0：超级管理员 1：车场管理员 2：单位管理员）
     */
    private Integer userType;

    /**
     * 登录版本 校验用户是否重复登录
     */
    private String version;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    public LoginUser() {
    }

    public LoginUser(Integer userId, String currentPark, SysUserEntity user, Set<String> permissions) {
        this.userId = userId;
        this.currentPark = currentPark;
        this.user = user;
        this.userType = user.getUserType();
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getCurrentPark() {
        return currentPark;
    }

    public void setCurrentPark(String currentPark) {
        this.currentPark = currentPark;
    }

    public SysUserEntity getUser() {
        return user;
    }

    public void setUser(SysUserEntity user) {
        this.user = user;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

}
