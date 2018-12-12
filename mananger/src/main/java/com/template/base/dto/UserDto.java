package com.template.base.dto;

import lombok.Data;

/**
 * @Title: UserDto
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/11 18:16
 */
@Data
public class UserDto {

    protected String loginName;

    protected String loginPassword;

    protected String userName;

    private String newPassword;

    private String rememberMe;
}
