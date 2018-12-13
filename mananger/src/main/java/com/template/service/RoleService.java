package com.template.service;

import com.template.base.domain.Role;
import com.template.response.PageDataResult;
import com.template.response.ResultData;

import java.util.List;

/**
 * @Title: RoleService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/13 10:53
 */
public interface RoleService {

    PageDataResult getRoleList(Integer pageNum, Integer pageSize);

    ResultData getRoles();

    ResultData updateRole(Role role);

    ResultData delRole(Integer id);

    ResultData addRole(Role role);


}
