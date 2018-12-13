package com.template.service;

import com.template.base.domain.User;
import com.template.response.PageDataResult;
import com.template.response.ResultData;

/**
 * @Title: UserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:01
 */
public interface UserService {

    PageDataResult getUserList(Integer pageNum,Integer ageSize);

    ResultData addUser(User user);

    ResultData updateUser(User user);

    ResultData delUser(Long id);

}
