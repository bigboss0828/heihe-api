package com.transpeed.personnel.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: PersonnelImportDto
 * @Params:
 * @Author zhangwenxiang
 * @Date: 2023/11/30 17:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelImportDto {

    private String personLevel;

    private String departName;

}
