package com.transpeed.park.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.park.entity.ParkSettingEntity;
import com.transpeed.park.mapper.ParkSettingMapper;
import com.transpeed.park.service.IParkSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: ParkSettingServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/20 9:08
 * @Version 1.0
 */
@Service
public class ParkSettingServiceImpl implements IParkSettingService {

    @Autowired
    private ParkSettingMapper parkSettingMapper;

    /**
     * 根据停车场编码获取设置信息
     * @param parkCode
     * @return
     */
    @Override
    public ParkSettingEntity settingByParkCode(String parkCode) {
        return this.parkSettingMapper.selectOne(Wrappers.<ParkSettingEntity>lambdaQuery()
                .eq(ParkSettingEntity::getParkCode, parkCode)
                .last(SystemConstants.SQL_LIMIT)
        );
    }

    @Override
    public int addSetting(ParkSettingEntity parkSettingEntity) {
        return this.parkSettingMapper.insert(parkSettingEntity);
    }


    /**
     * 更新停车场设置
     * @param parkSettingEntity
     * @return
     */
    @Override
    public int updateSetting(ParkSettingEntity parkSettingEntity) {
        return this.parkSettingMapper.updateById(parkSettingEntity);
    }
}
