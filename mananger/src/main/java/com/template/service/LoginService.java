package com.template.service;

import com.template.base.domain.User;
import com.template.base.dto.LoginDto;
import com.template.response.ResultData;

/**
 * @Title: LoginService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:07
 */
public interface LoginService {
    ResultData loginUser(LoginDto userDto);

    ResultData getUserPerms(User user);
}
