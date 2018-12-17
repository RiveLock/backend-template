package com.template.base.dao;

import com.template.base.domain.Role;
import com.template.base.domain.base.BaseRole;
import com.template.base.domain.criteria.RoleCriteria;
import java.util.List;

public interface RoleDao {
    int insert(BaseRole record);

    Role selectOne(Integer pk);

    Role selectOne(RoleCriteria criteria);

    List<Role> selectList(RoleCriteria criteria);

    int count(RoleCriteria criteria);

    int update(BaseRole record);

    int update(BaseRole record, RoleCriteria criteria);

    int delete(Integer pk);

    int delete(RoleCriteria criteria);
}