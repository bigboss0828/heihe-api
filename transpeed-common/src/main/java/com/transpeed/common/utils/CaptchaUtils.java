package com.transpeed.common.utils;

import com.wf.captcha.ArithmeticCaptcha;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hanshuai
 * @title: CaptchaUtils
 * @description: 验证码生成工具类
 * @date 2023/5/26 14:15
 */
public class CaptchaUtils {

    /**
     * 生成验证码
     *
     * @return base64格式图片
     */
    public static Map<String, String> createCaptcha() {
        // 算术类型（长，宽，几个数的运算）
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(2);  // 几位数运算，默认是两位
        // 图片英语字母数字类型
        //SpecCaptcha captcha = new SpecCaptcha(130, 48);
        // 英语字母数字gif类型的
        //GifCaptcha captcha = new GifCaptcha(130, 48,4);
        // 中文类型的
        //ChineseCaptcha captcha = new ChineseCaptcha(130, 48,3);
        // 中文gif类型
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48,4);
        // 输出验证码
        Map<String, String> result = new HashMap<>();
        result.put("base64", captcha.toBase64());
        result.put("result", captcha.text());
        return result;
    }

}
