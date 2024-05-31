package com.transpeed.personnel.common.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.transpeed.common.core.dto.BaseDto;
import com.transpeed.common.core.page.DataGridView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: PersonnelSelectDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/9/13 19:24
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelSelectDto extends BaseDto {

    /**
     * 人员姓名
     */
    private String ownerName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * IC卡号
     */
    private String icNo;

    /**
     * 人员等级（p_person_level的type_key字段）
     */
    private String personLevel;

    /**
     * IC卡状态(0-正常 1-禁用)
     */
    private String icStatus;

}
