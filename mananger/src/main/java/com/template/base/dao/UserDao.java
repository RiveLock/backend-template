package com.template.base.dao;

import com.template.base.domain.User;
import com.template.base.domain.base.BaseUser;
import com.template.base.domain.criteria.UserCriteria;
import java.util.List;

public interface UserDao {
    int insert(BaseUser record);

    User selectOne(Long pk);

    User selectOne(UserCriteria criteria);

    List<User> selectList(UserCriteria criteria);

    int count(UserCriteria criteria);

    int update(BaseUser record);

    int update(BaseUser record, UserCriteria criteria);

    int delete(Long pk);

    int delete(UserCriteria criteria);
}