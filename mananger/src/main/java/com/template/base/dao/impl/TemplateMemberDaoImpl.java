package com.template.base.dao.impl;


import com.template.base.dao.TemplateMemberDao;
import com.template.base.domain.TemplateMember;
import com.template.base.domain.base.BaseTemplateMember;
import com.template.base.domain.criteria.TemplateMemberCriteria;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("templateMemberDao")
public class TemplateMemberDaoImpl implements TemplateMemberDao {
    private static final String NAMESPACE = "com.template.base.dao.impl.TemplateMemberDaoImpl.";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(BaseTemplateMember record) {
        return sqlSessionTemplate.insert(NAMESPACE + "insert", record);
    }

    public TemplateMember selectOne(Long pk) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByPk", pk);
    }

    public TemplateMember selectOne(TemplateMemberCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByCriteria", criteria);
    }

    public List<TemplateMember> selectList(TemplateMemberCriteria criteria) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectList", criteria);
    }

    public int count(TemplateMemberCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "count", criteria);
    }

    public int update(BaseTemplateMember record) {
        return sqlSessionTemplate.update(NAMESPACE + "updateByPk", record);
    }

    public int update(BaseTemplateMember record, TemplateMemberCriteria criteria) {
        Map<String, Object> param = new HashMap<>();
        param.put("record", record);
        param.put("criteria", criteria);
        return sqlSessionTemplate.update(NAMESPACE + "updateByCriteria", param);
    }

    public int delete(Long pk) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByPk", pk);
    }

    public int delete(TemplateMemberCriteria criteria) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByCriteria", criteria);
    }
}