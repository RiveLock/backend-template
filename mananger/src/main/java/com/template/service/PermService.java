package com.template.service;

import com.template.base.domain.Permission;
import com.template.response.PageDataResult;

import java.util.List;

/**
 * @Title: PermService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 18:53
 */
public interface PermService {

    PageDataResult getPermissionList(Integer pageNum, Integer pageSize);

    List<Permission> parentPermissionList();
}
