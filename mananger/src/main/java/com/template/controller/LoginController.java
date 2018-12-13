package com.template.controller;


import com.template.base.dao.UserDao;
import com.template.base.domain.User;
import com.template.base.domain.criteria.UserCriteria;
import com.template.base.dto.UserDto;
import com.template.response.Fail;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.service.LoginService;
import com.template.utils.DateUtils;
import com.template.utils.DigestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotNull;

/**
 * Created jixinshi on 2018-11-27.
 */
@Api(description = "登入接口")
@RequestMapping("mananger")
@RestController
@Slf4j
public class LoginController {


    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginService loginService;


    /**
     * 登陆
     * @param userDto
     * @return
     */
    @ApiOperation(value = "登陆用户")
    @PostMapping(value = "/login")
    public ResultData loginUser(@RequestBody @NotNull UserDto userDto){
        return loginService.loginUser(userDto);
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
            return ResultData.fail(Fail.USER_NOT_LOGIN_NULL.code, Fail.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(userDto.getLoginPassword())){
            return ResultData.fail(Fail.USER_NOT_PASSWORD_NULL.code, Fail.USER_NOT_PASSWORD_NULL.description);
        }

        User user = userDao.selectOne(UserCriteria.loginNameEqualTo(userDto.getLoginName()).setLimit(1L));
        if (user != null){
            return ResultData.fail(Fail.USERNAME_EXIST.code, Fail.USERNAME_EXIST.description);
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
            return ResultData.fail(Fail.USER_REGISTER_FAIL.code, Fail.USER_REGISTER_FAIL.description);
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

        /*User user = userDao.selectOne(UserCriteria.loginNameEqualTo(userDto.getLoginName()).setLimit(1L));
        if (user == null){
            return "login name is  exist!";
        }

        if (!userDto.getLoginPassword().equals(userDto.getLoginName())){
            return "original password is wrong!";
        }

        user.setLoginPassword(userDto.getNewPassword());
        user.setUpdateTime(DateUtils.getCurrentDate());*/

        //userDao.update(user);

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
