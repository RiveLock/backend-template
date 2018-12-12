package com.template.base.dao.impl;

import com.template.base.dao.PermissionDao;
import com.template.base.domain.Permission;
import com.template.base.domain.base.BasePermission;
import com.template.base.domain.criteria.PermissionCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

@Component("permissionDao")
public class PermissionDaoImpl implements PermissionDao {
    private static final String NAMESPACE = "com.template.base.dao.impl.PermissionDaoImpl.";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(BasePermission record) {
        return sqlSessionTemplate.insert(NAMESPACE + "insert", record);
    }

    public Permission selectOne(Integer pk) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByPk", pk);
    }

    public Permission selectOne(PermissionCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByCriteria", criteria);
    }

    public List<Permission> selectList(PermissionCriteria criteria) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectList", criteria);
    }

    public int count(PermissionCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "count", criteria);
    }

    public int update(BasePermission record) {
        return sqlSessionTemplate.update(NAMESPACE + "updateByPk", record);
    }

    public int update(BasePermission record, PermissionCriteria criteria) {
        Map<String, Object> param = new HashMap<>();
        param.put("record", record);
        param.put("criteria", criteria);
        return sqlSessionTemplate.update(NAMESPACE + "updateByCriteria", param);
    }

    public int delete(Integer pk) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByPk", pk);
    }

    public int delete(PermissionCriteria criteria) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByCriteria", criteria);
    }
}