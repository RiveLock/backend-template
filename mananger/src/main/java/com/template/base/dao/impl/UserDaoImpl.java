package com.template.base.dao.impl;

import com.template.base.dao.UserDao;
import com.template.base.domain.User;
import com.template.base.domain.base.BaseUser;
import com.template.base.domain.criteria.UserCriteria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

@Component("userDao")
public class UserDaoImpl implements UserDao {
    private static final String NAMESPACE = "com.template.base.dao.impl.UserDaoImpl.";

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(BaseUser record) {
        return sqlSessionTemplate.insert(NAMESPACE + "insert", record);
    }

    public User selectOne(Long pk) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByPk", pk);
    }

    public User selectOne(UserCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "selectByCriteria", criteria);
    }

    public List<User> selectList(UserCriteria criteria) {
        return sqlSessionTemplate.selectList(NAMESPACE + "selectList", criteria);
    }

    public int count(UserCriteria criteria) {
        return sqlSessionTemplate.selectOne(NAMESPACE + "count", criteria);
    }

    public int update(BaseUser record) {
        return sqlSessionTemplate.update(NAMESPACE + "updateByPk", record);
    }

    public int update(BaseUser record, UserCriteria criteria) {
        Map<String, Object> param = new HashMap<>();
        param.put("record", record);
        param.put("criteria", criteria);
        return sqlSessionTemplate.update(NAMESPACE + "updateByCriteria", param);
    }

    public int delete(Long pk) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByPk", pk);
    }

    public int delete(UserCriteria criteria) {
        return sqlSessionTemplate.delete(NAMESPACE + "deleteByCriteria", criteria);
    }
}