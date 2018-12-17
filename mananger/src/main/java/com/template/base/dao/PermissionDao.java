package com.template.base.dao;

import com.template.base.domain.Permission;
import com.template.base.domain.base.BasePermission;
import com.template.base.domain.criteria.PermissionCriteria;
import java.util.List;

public interface PermissionDao {
    int insert(BasePermission record);

    Permission selectOne(Integer pk);

    Permission selectOne(PermissionCriteria criteria);

    List<Permission> selectList(PermissionCriteria criteria);

    int count(PermissionCriteria criteria);

    int update(BasePermission record);

    int update(BasePermission record, PermissionCriteria criteria);

    int delete(Integer pk);

    int delete(PermissionCriteria criteria);
}