package com.transpeed.web.controller.login;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.transpeed.common.constant.business.system.user.UserConstants;
import com.transpeed.common.core.AjaxResult;
import com.transpeed.common.core.domain.model.LoginBody;
import com.transpeed.common.core.domain.model.LoginUser;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.system.service.ISysMenuService;
import com.transpeed.common.utils.CaptchaUtils;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.framework.web.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hanshuai
 * @title: LoginController
 * @description: 用户登录
 * @date 2023/3/16 18:00
 */
@RestController
public class LoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 生成验证码 时效60秒
     *
     * @return base64格式验证码
     */
    @GetMapping(value = "/captcha")
    public AjaxResult verifyCode() {
        Map<String, String> captcha = CaptchaUtils.createCaptcha();
        String uuid = IdUtil.fastSimpleUUID();
        redisCache.setCacheObject(UserConstants.CAPTCHA_CODE_KEY + uuid, captcha.get("result"), UserConstants.CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        JSONObject result = new JSONObject();
        result.put("base64", captcha.get("base64"));
        result.put("uuid", uuid);
        return AjaxResult.success(result);
    }

    /**
     * 用户登录
     *
     * @param loginBody 登录实体
     * @return 成功：返回token
     */
    @PostMapping("/login")
    public AjaxResult login(@Validated @RequestBody LoginBody loginBody) {
        // 校验登录信息
        LoginUser loginUser = this.loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        // 每次登录删除菜单缓存
        redisCache.deleteObject(UserConstants.SYSTEM_MENU_CACHE + loginBody.getUsername());
        return AjaxResult.success(loginUser);
    }

    /**
     * 获取菜单权限
     *
     * @return 菜单信息
     */
    @GetMapping("/userInfo")
    public AjaxResult getUserInfo() {
        Map<String, Object> userInfo;
        // 判断Redis是否有缓存
        if (redisCache.hasKey(UserConstants.SYSTEM_MENU_CACHE + SecurityUtils.getUsername())) {
            userInfo = redisCache.getCacheObject(UserConstants.SYSTEM_MENU_CACHE + SecurityUtils.getUsername());
        } else {
            userInfo = menuService.getUserInfo(SecurityUtils.getUserId());
        }
        if (userInfo != null && userInfo.size() > 0) {
            // 更新菜单缓存时间为30分钟
            redisCache.setCacheObject(UserConstants.SYSTEM_MENU_CACHE + SecurityUtils.getUsername(), userInfo, UserConstants.SYSTEM_MENU_CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
        }
        return AjaxResult.success(userInfo);
    }

}
