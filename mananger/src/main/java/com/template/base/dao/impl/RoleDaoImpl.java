package com.template.base.dao.impl;

import com.template.base.dao.RoleDao;
import com.template.base.domain.Role;
import com.template.base.domain.base.BaseRole;
import com.template.base.domain.criteria.RoleCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

@Component("roleDao")
public class RoleDaoImpl implements RoleDao {
    private static final String NAMESPACE = "com.template.base.dao.impl.RoleDaoImpl.";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(BaseRole record) {
        return sqlSessionTemplate.insert(NAMESPACE + "insert", record);
    }

    public Role selectOne(Integer pk) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByPk", pk);
    }

    public Role selectOne(RoleCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByCriteria", criteria);
    }

    public List<Role> selectList(RoleCriteria criteria) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectList", criteria);
    }

    public int count(RoleCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "count", criteria);
    }

    public int update(BaseRole record) {
        return sqlSessionTemplate.update(NAMESPACE + "updateByPk", record);
    }

    public int update(BaseRole record, RoleCriteria criteria) {
        Map<String, Object> param = new HashMap<>();
        param.put("record", record);
        param.put("criteria", criteria);
        return sqlSessionTemplate.update(NAMESPACE + "updateByCriteria", param);
    }

    public int delete(Integer pk) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByPk", pk);
    }

    public int delete(RoleCriteria criteria) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByCriteria", criteria);
    }
}