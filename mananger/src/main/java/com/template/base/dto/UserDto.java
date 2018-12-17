package com.template.base.dto;

import com.template.base.domain.User;
import lombok.Data;

/**
 * @Title: UserDto
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/13 15:18
 */
@Data
public class UserDto extends User {
    private String roleName;
}
