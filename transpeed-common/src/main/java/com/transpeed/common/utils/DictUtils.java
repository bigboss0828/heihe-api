package com.transpeed.common.utils;

import com.transpeed.common.constant.business.system.dict.DictConstants;
import com.transpeed.common.core.domain.entity.SysDictDataEntity;
import com.transpeed.common.core.redis.RedisCache;
import com.transpeed.common.utils.spring.SpringUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author hanshuai
 * @title: DictUtils
 * @description: 字典工具类
 * @date 2022/2/25 10:15
 */
public class DictUtils {

    /**
     * 分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 设置字典缓存
     * @param key 参数键
     * @param dictDatas 字典数据列表
     */
    public static void setDictCache(String key, List<SysDictDataEntity> dictDatas) {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * 获取字典缓存
     * @param key 参数键
     * @return dictDatas 字典数据列表
     */
    public static List<SysDictDataEntity> getDictCache(String key) {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     * @param dictType 字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根据字典类型和字典值获取字典标签
     * @param dictType 字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String getDictLabel(String dictType, String dictValue, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictDataEntity> datas = getDictCache(dictType);
        if (StringUtils.containsAny(separator, dictValue) && StringUtils.isNotEmpty(datas)) {
            for (SysDictDataEntity dict : datas) {
                for (String value : dictValue.split(separator)) {
                    if (value.equals(dict.getDictDataValue())) {
                        propertyString.append(dict.getDictDataLabel()).append(separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDictDataEntity dict : datas) {
                if (dictValue.equals(dict.getDictDataValue())) {
                    return dict.getDictDataLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator) {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictDataEntity> datas = getDictCache(dictType);
        if (StringUtils.containsAny(separator, dictLabel) && StringUtils.isNotEmpty(datas)) {
            for (SysDictDataEntity dict : datas) {
                for (String label : dictLabel.split(separator)) {
                    if (label.equals(dict.getDictDataLabel())) {
                        propertyString.append(dict.getDictDataValue()).append(separator);
                        break;
                    }
                }
            }
        } else {
            for (SysDictDataEntity dict : datas) {
                if (dictLabel.equals(dict.getDictDataLabel())) {
                    return dict.getDictDataValue().toString();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 删除指定字典缓存
     * @param key 字典键
     */
    public static void removeDictCache(String key) {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
    }

    /**
     * 清空字典缓存
     */
    public static void clearDictCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(DictConstants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey) {
        return DictConstants.SYS_DICT_KEY + configKey;
    }

}
