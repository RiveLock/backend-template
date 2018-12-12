package com.template.controller;


import com.template.base.dao.UserDao;
import com.template.base.domain.User;
import com.template.base.domain.criteria.UserCriteria;
import com.template.base.dto.UserDto;
import com.template.response.Error;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.utils.DateUtils;
import com.template.utils.DigestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created jixinshi on 2018-11-27.
 */
@Api("用户信息接口")
@RequestMapping("mananger")
@RestController
@Slf4j
public class LoginController {


    @Autowired
    private UserDao userDao;


    /**
     * 登陆
     * @param userDto
     * @return
     */
    @ApiOperation(value = "登陆用户")
    @PostMapping(value = "/login")
    public ResultData loginUser(@RequestBody @NotNull UserDto userDto){
        Map<String,Object> data = new HashedMap();

        if (StringUtils.isEmpty(userDto.getLoginName())){
            return ResultData.error(Error.USER_NOT_LOGIN_NULL.code,Error.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(userDto.getLoginPassword())){
            return ResultData.error(Error.USER_NOT_PASSWORD_NULL.code,Error.USER_NOT_PASSWORD_NULL.description);
        }

        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String userName = userDto.getLoginName().trim();
        String password = userDto.getLoginPassword().trim();
        String rememberMe = userDto.getRememberMe();

        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        // 设置 remenmberMe 的功能
        if (rememberMe != null && rememberMe.equals("on")) {
            token.setRememberMe(true);
        }
        try{
            subject.login(token);

            // 登录成功
            User user = (User) subject.getPrincipal();
            data.put("token",user.getLoginName());
            log.info(userName+"登入系统");

            return ResultData.success(Success.USER_LOGIN_SUCCESS.code,Success.USER_LOGIN_SUCCESS.description).with(data);
        }catch (UnknownAccountException e) {
            log.error(userName+"账号不存在");
            return ResultData.error(Error.USERNAME_NOT_EXIST.code,Error.USERNAME_NOT_EXIST.description);
        }catch (AuthenticationException e){
            log.error(userName+"密码错误");
            return ResultData.error(Error.USER_PASSWORD_FAIL.code,Error.USER_PASSWORD_FAIL.description);
        }

    }

    /**
     * 注册
     * @param userDto
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/add/user")
    public ResultData addUser(@RequestBody @NotNull UserDto userDto){

        if (StringUtils.isEmpty(userDto.getLoginName())){
            return ResultData.error(Error.USER_NOT_LOGIN_NULL.code,Error.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(userDto.getLoginPassword())){
            return ResultData.error(Error.USER_NOT_PASSWORD_NULL.code,Error.USER_NOT_PASSWORD_NULL.description);
        }

        User user = userDao.selectOne(UserCriteria.loginNameEqualTo(userDto.getLoginName()).setLimit(1L));
        if (user != null){
            return ResultData.error(Error.USERNAME_EXIST.code,Error.USERNAME_EXIST.description);
        }
        //加密
        String loginPassword = userDto.getLoginPassword();
        loginPassword = DigestUtils.Md5(userDto.getLoginName(),loginPassword);

        user.setLoginName(userDto.getLoginName());
        user.setLoginPassword(loginPassword);
        user.setCreateTime(DateUtils.getCurrentDate());
        user.setUpdateTime(DateUtils.getCurrentDate());
        user.setDelFlag(1);

        int result = userDao.insert(user);
        if(result == 0 ){
            return ResultData.error(Error.USER_REGISTER_FAIL.code,Error.USER_REGISTER_FAIL.description);
        }

        return ResultData.success(Success.USER_REGISTER_SUCCESS.code,Success.USER_REGISTER_SUCCESS.description);
    }

    /**
     * 修改
     * @param userDto
     * @return
     */
    @ApiOperation(value = "修改用户")
    @PostMapping(value = "/motify/user")
    public String motifyUser(@RequestBody @NotNull UserDto userDto){

        if (StringUtils.isEmpty(userDto.getLoginName())){
            return "login name is empty!";
        }

        if (StringUtils.isEmpty(userDto.getLoginPassword())){
            return "login password is empty!";
        }

        if (StringUtils.isEmpty(userDto.getNewPassword())){
            return "new password is empty!";
        }

        User user = userDao.selectOne(UserCriteria.loginNameEqualTo(userDto.getLoginName()).setLimit(1L));
        if (user == null){
            return "login name is  exist!";
        }

        if (!userDto.getLoginPassword().equals(userDto.getLoginName())){
            return "original password is wrong!";
        }

        user.setLoginPassword(userDto.getNewPassword());
        user.setUpdateTime(DateUtils.getCurrentDate());

        userDao.update(user);

        return "motify password success";
    }

    /**
     *
     * 功能描述: 退出系统
     *
     * @param:
     * @return:
     * @auther: youqing
     * @ate: 2018/12/11 18:47
     */
    @ApiOperation(value = "退出系统")
    @GetMapping("logout")
    public ResultData logout(){
        log.info("退出系统");
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); // shiro底层删除session的会话信息
        return ResultData.success(Success.LOGOUT_SUCCESS.code,Success.LOGOUT_SUCCESS.description);
    }

}
