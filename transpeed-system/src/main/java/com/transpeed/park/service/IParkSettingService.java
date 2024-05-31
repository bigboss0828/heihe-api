package com.transpeed.park.service;

import com.transpeed.park.entity.ParkSettingEntity;

/**
 * @title: IParkSettingService
 * @Author zhangwenxiang
 * @Date: 2023/9/20 9:07
 * @Version 1.0
 */

public interface IParkSettingService {

    /**
     * 根据停车场编码获取设置信息
     * @param parkCode
     * @return
     */
    ParkSettingEntity settingByParkCode(String parkCode);

    /**
     * 新增设置
     * @param parkSettingEntity
     * @return
     */
    int addSetting(ParkSettingEntity parkSettingEntity);

    /**
     * 更新停车场设置
     * @param parkSettingEntity
     * @return
     */
    int updateSetting(ParkSettingEntity parkSettingEntity);

}
