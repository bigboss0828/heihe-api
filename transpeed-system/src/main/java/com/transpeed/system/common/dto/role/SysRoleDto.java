package com.transpeed.system.common.dto.role;

import com.transpeed.common.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hanshuai
 * @title: SysRoleDto
 * @description: 角色接收前端对象
 * @date 2023/6/12 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleDto extends BaseDto {

    /**
     * 主键ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色状态（0：正常 1：停用）
     */
    private Integer roleStatus;

    /**
     * 授权菜单ID集合
     */
    @NotNull(message = "授权菜单不能为空")
    private List<Integer> menuIdList;

}
