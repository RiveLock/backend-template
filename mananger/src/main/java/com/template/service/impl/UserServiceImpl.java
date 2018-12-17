package com.template.service.impl;

import com.template.base.dao.RoleDao;
import com.template.base.dao.UserDao;
import com.template.base.domain.Role;
import com.template.base.domain.User;
import com.template.base.domain.criteria.RoleCriteria;
import com.template.base.domain.criteria.UserCriteria;
import com.template.base.dto.UserDto;
import com.template.response.Fail;
import com.template.response.PageDataResult;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.service.UserService;
import com.template.utils.DateUtils;
import com.template.utils.DigestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: UserServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:02
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageDataResult getUserList(Integer pageNum, Integer pageSize) {
        PageDataResult pdr = new PageDataResult();
        // 获取用户列表
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.setLimit((long) pageSize);
        userCriteria.setOffset((long) ((pageNum-1)*pageSize));
        List<User> users = userDao.selectList(userCriteria.idIsNotNull());
        List<UserDto> userDtos = new ArrayList <>();
        for(User user : users){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            Integer roleId =  user.getRoleId();
            if(roleId != null){
                Role role = roleDao.selectOne(RoleCriteria.idEqualTo(user.getRoleId()));
                userDto.setRoleName(role.getRoleName());
            }
            userDtos.add(userDto);
        }
        pdr.setTotals(userDtos.size());
        pdr.setList(userDtos);
        return pdr;
    }

    @Override
    public ResultData addUser(User user) {
        try {
            String loginName = user.getLoginName().trim();
            String loginPassword = user.getLoginPassword().trim();

            if (StringUtils.isEmpty(loginName)){
                log.error("用户[新增]，结果=登录名为空！");
                return ResultData.fail(Fail.USER_NOT_LOGIN_NULL.code, Fail.USER_NOT_LOGIN_NULL.description);
            }
            if (StringUtils.isEmpty(loginPassword)){
                log.error("用户[新增]，结果=密码为空！");
                return ResultData.fail(Fail.USER_NOT_PASSWORD_NULL.code, Fail.USER_NOT_PASSWORD_NULL.description);
            }

            UserCriteria userCriteria = new UserCriteria();
            User old = userDao.selectOne(userCriteria.andLoginNameEqualTo(loginName));
            if(old != null){
                log.error("用户[新增]，结果=登录名已存在！");
                return ResultData.fail(Fail.USER_LOGINNAME_ERROR.code,Fail.USER_LOGINNAME_ERROR.description);
            }

            //密码加密
            loginPassword = DigestUtils.Md5(loginName,loginPassword);
            user.setLoginPassword(loginPassword);

            user.setCreateTime(DateUtils.getCurrentDate());
            user.setUpdateTime(DateUtils.getCurrentDate());
            user.setDelFlag(1);
            int result = userDao.insert(user);
            if(result == 0){
                log.error("用户[新增]，结果=新增失败！");
                return ResultData.fail(Fail.USER_REGISTER_FAIL.code, Fail.USER_REGISTER_FAIL.description);
            }
            log.info("用户[新增]，结果=新增成功！");
            return ResultData.success(Success.USER_REGISTER_SUCCESS.code,Success.USER_REGISTER_SUCCESS.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户[新增]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData updateUser(User user) {
        try {
            String loginName = user.getLoginName().trim();
            String loginPassword = user.getLoginPassword().trim();

            if (StringUtils.isEmpty(loginName)){
                log.error("用户[新增]，结果=登录名为空！");
                return ResultData.fail(Fail.USER_NOT_LOGIN_NULL.code, Fail.USER_NOT_LOGIN_NULL.description);
            }
            if (StringUtils.isEmpty(loginPassword)){
                log.error("用户[新增]，结果=密码为空！");
                return ResultData.fail(Fail.USER_NOT_PASSWORD_NULL.code, Fail.USER_NOT_PASSWORD_NULL.description);
            }

            UserCriteria userCriteria = new UserCriteria();
            User old = userDao.selectOne(userCriteria.andLoginNameEqualTo(loginName).andIdNotEqualTo(user.getId()));
            if(old != null){
                log.error("用户[修改]，结果=登录名已存在！");
                return ResultData.fail(Fail.USER_LOGINNAME_ERROR.code,Fail.USER_LOGINNAME_ERROR.description);
            }

            //密码加密
            loginPassword = DigestUtils.Md5(loginName,loginPassword);
            user.setLoginPassword(loginPassword);
            user.setUpdateTime(DateUtils.getCurrentDate());
            user.setDelFlag(1);
            int result = userDao.update(user);
            if(result == 0){
                log.error("用户[修改]，结果=新增失败！");
                return ResultData.fail(Fail.USER_UPDATE_FAIL.code, Fail.USER_UPDATE_FAIL.description);
            }
            log.info("用户[修改]，结果=新增成功！");
            return ResultData.success(Success.USER_UPDATE_SUCCESS.code,Success.USER_UPDATE_SUCCESS.description);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户[修改]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }

    @Override
    public ResultData delUser(Long id) {
        try{
            int result = userDao.delete(id);
            if(result == 0){
                log.error("用户[删除]，结果=删除失败！");
                return ResultData.fail(Fail.USER_DELETE_FAIL.code,Fail.USER_DELETE_FAIL.description);
            }
            log.info("用户[删除]，结果=删除成功！");
            return ResultData.success(Success.ROLE_DELETE_SUCCESS.code,Success.ROLE_DELETE_SUCCESS.description);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("用户[删除]异常！", e);
            return ResultData.fail(Fail.SYSTEM_FAIL.code,Fail.SYSTEM_FAIL.description);
        }
    }
}
