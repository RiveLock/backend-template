package com.template.service.impl;

import com.template.base.dao.UserDao;
import com.template.base.domain.User;
import com.template.base.domain.criteria.UserCriteria;
import com.template.response.PageDataResult;
import com.template.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: UserServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:02
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public PageDataResult getUserList(Integer pageNum, Integer pageSize) {
        PageDataResult pdr = new PageDataResult();
        // 获取用户列表
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.setLimit((long) pageSize);
        userCriteria.setOffset((long) ((pageNum-1)*pageSize));
        List<User> users = userDao.selectList(userCriteria.idIsNotNull());
        pdr.setTotals(users.size());
        pdr.setList(users);
        return pdr;
    }
}
