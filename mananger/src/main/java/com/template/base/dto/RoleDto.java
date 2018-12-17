package com.template.base.dto;

import lombok.Data;

/**
 * @Title: RoleDto
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/13 11:15
 */
@Data
public class RoleDto {
    private Integer id;

    private String roleName;

    private String roleDesc;

    private String permissionIds;
    private String permissions;

    private String createTime;

    private String updateTime;

    private Integer roleStatus;

}
