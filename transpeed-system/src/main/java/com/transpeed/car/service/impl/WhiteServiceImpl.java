package com.transpeed.car.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.transpeed.car.common.dto.WhiteSelectDto;
import com.transpeed.car.entity.WhiteEntity;
import com.transpeed.car.mapper.WhiteMapper;
import com.transpeed.car.service.IWhiteService;
import com.transpeed.common.constant.system.SystemConstants;
import com.transpeed.common.core.page.DataGridView;
import com.transpeed.common.exception.ServiceException;
import com.transpeed.common.utils.SecurityUtils;
import com.transpeed.common.utils.StringUtils;
import com.transpeed.personnel.entity.PersonnelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @title: WhiteServiceImpl
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/14 19:06
 * @Version 1.0
 */
@Slf4j
@Service
public class WhiteServiceImpl implements IWhiteService {

    @Autowired
    private WhiteMapper whiteMapper;

    /**
     * 分页查询白名单车辆
     *
     * @param dto
     * @return
     */
    @Override
    public DataGridView listForWhite(WhiteSelectDto dto) {
        Page<WhiteEntity> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        this.whiteMapper.selectPage(page, Wrappers.<WhiteEntity>lambdaQuery()
                .like(StringUtils.isNotEmpty(dto.getAuthLane()), WhiteEntity::getAuthLane, dto.getAuthLane())
                .like(StringUtils.isNotEmpty(dto.getObuId()), WhiteEntity::getObuId, dto.getObuId())
                .like(StringUtils.isNotEmpty(dto.getPlate()), WhiteEntity::getPlate, dto.getPlate())
                .like(StringUtils.isNotEmpty(dto.getOwnerName()), WhiteEntity::getOwnerName, dto.getOwnerName())
                .eq(StringUtils.isNotEmpty(dto.getCarType()), WhiteEntity::getCarType, dto.getCarType())
                .eq(StringUtils.isNotEmpty(dto.getWhiteType()), WhiteEntity::getWhiteType, dto.getWhiteType())
                .eq(StringUtils.isNotEmpty(dto.getOverdue()), WhiteEntity::getOverdue, dto.getOverdue())
                .ge(dto.getBeginTime() != null, WhiteEntity::getStartTime, dto.getBeginTime())
                .le(dto.getEndTime() != null, WhiteEntity::getEndTime, dto.getEndTime())
                .orderByDesc(WhiteEntity::getId)
        );
        return new DataGridView(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

    /**
     * 新增白名单车辆
     *
     * @param whiteEntity
     * @return
     */
    @Override
    public int addWhiteCar(WhiteEntity whiteEntity) {
        return this.whiteMapper.insert(whiteEntity);
    }

    /**
     * 修改白名单车辆
     *
     * @param whiteEntity
     * @return
     */
    @Override
    public int updateWhiteCar(WhiteEntity whiteEntity) {
        return this.whiteMapper.updateById(whiteEntity);
    }

    /**
     * 删除白名单车辆
     *
     * @param ids
     * @return
     */
    @Override
    public int delWhiteCar(Integer[] ids) {
        return this.whiteMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 校验车牌重复
     *
     * @param whiteEntity
     * @return
     */
    @Override
    public String checkPlateUnique(WhiteEntity whiteEntity) {
        Integer id = StringUtils.isNull(whiteEntity.getId()) ? -1 : whiteEntity.getId();
        WhiteEntity whiteCar = this.whiteMapper.selectOne(Wrappers.<WhiteEntity>lambdaQuery()
                .eq(StringUtils.isNotEmpty(whiteEntity.getPlate()), WhiteEntity::getPlate, whiteEntity.getPlate())
                .or()
                .eq(StringUtils.isNotEmpty(whiteEntity.getObuId()), WhiteEntity::getObuId, whiteEntity.getObuId())
                .last(SystemConstants.SQL_LIMIT)
        );
        if (StringUtils.isNotNull(whiteCar) && whiteCar.getId().longValue() != id.longValue()) {
            return SystemConstants.NOT_UNIQUE;
        }
        return SystemConstants.UNIQUE;
    }

    @Override
    public String importWhite(List<WhiteEntity> whites, String whiteType, String authLane) {
        if (StringUtils.isNull(whites) || whites.size() == 0) {
            throw new ServiceException("导入白名单数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (WhiteEntity white : whites) {
            try {
                if (StringUtils.isNotEmpty(white.getObuId()) || StringUtils.isNotEmpty(white.getPlate())) {
                    if (SystemConstants.NOT_UNIQUE.equals(this.checkPlateUnique(white))) {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、 导入" + white.getPlate() + "失败！当前车牌或者obu已经存在！");
                        continue;
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、 导入失败！车牌和obu不能都为空！");
                    continue;
                }
                white.setWhiteType(whiteType);
                white.setParkCode(SecurityUtils.getCurrentPark());
                white.setAuthLane(authLane);
                this.whiteMapper.insert(white);
                if (StringUtils.isNotEmpty(white.getPlate())) {
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、 导入白名单" + white.getPlate() + "成功！");
                } else {
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、 导入白名单" + white.getObuId() + "成功！");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、 导入白名单" + white.getPlate() + "失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
