package com.template.service.impl;

import com.template.base.dao.UserDao;
import com.template.base.domain.User;
import com.template.base.dto.LoginDto;
import com.template.response.Fail;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Title: LoginServiceImpl
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 19:08
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Override
    public ResultData loginUser(LoginDto userDto) {
        Map<String,Object> data = new HashedMap();

        if (StringUtils.isEmpty(userDto.getLoginName())){
            return ResultData.fail(Fail.USER_NOT_LOGIN_NULL.code, Fail.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(userDto.getLoginPassword())){
            return ResultData.fail(Fail.USER_NOT_PASSWORD_NULL.code, Fail.USER_NOT_PASSWORD_NULL.description);
        }

        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String userName = userDto.getLoginName().trim();
        String password = userDto.getLoginPassword().trim();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try{
            subject.login(token);

            // 登录成功
            User user = (User) subject.getPrincipal();
            data.put("token",user.getLoginName());
            log.info(userName+"登入系统");

            return ResultData.success(Success.USER_LOGIN_SUCCESS.code,Success.USER_LOGIN_SUCCESS.description).with(data);
        }catch (UnknownAccountException e) {
            log.error(userName+"账号不存在");
            return ResultData.fail(Fail.USERNAME_NOT_EXIST.code, Fail.USERNAME_NOT_EXIST.description);
        }catch (AuthenticationException e){
            log.error(userName+"密码错误");
            return ResultData.fail(Fail.USER_PASSWORD_FAIL.code, Fail.USER_PASSWORD_FAIL.description);
        }
    }
}
