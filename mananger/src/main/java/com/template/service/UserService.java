package com.template.service;

import com.template.response.PageDataResult;

/**
 * @Title: UserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:01
 */
public interface UserService {

    PageDataResult getUserList(Integer pageNum,Integer pageSize);

}
