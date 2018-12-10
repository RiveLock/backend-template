package com.template.controller;


import com.template.base.dao.TemplateMemberDao;
import com.template.base.domain.TemplateMember;
import com.template.base.domain.criteria.TemplateMemberCriteria;
import com.template.base.dto.TemplateMemberDto;
import com.template.response.Error;
import com.template.response.ResultData;
import com.template.response.Success;
import com.template.utils.DigestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created jixinshi on 2018-11-27.
 */
@Api("用户信息 接口")
@RequestMapping("member")
@RestController
@Slf4j
public class MemberController {

    @Resource
    private TemplateMemberDao templateMemberDao;


    /**
     * 登陆
     * @param memberDto
     * @return
     */
    @ApiOperation(value = "登陆用户")
    @PostMapping(value = "/login/user")
    public ResultData loginUser(@RequestBody @NotNull TemplateMemberDto memberDto){

        if (StringUtils.isEmpty(memberDto.getLoginName())){
            return ResultData.error(Error.USER_NOT_LOGIN_NULL.code,Error.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(memberDto.getLoginPassword())){
            return ResultData.error(Error.USER_NOT_PASSWORD_NULL.code,Error.USER_NOT_PASSWORD_NULL.description);
        }

        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();

        String userName = memberDto.getLoginName().trim();
        String password = memberDto.getLoginPassword().trim();


        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

        try{
            subject.login(token);
            log.info(userName+"登入系统");
        }catch (UnknownAccountException e) {
            log.error(userName+"账号不存在");
            return ResultData.error(Error.USERNAME_NOT_EXIST.code,Error.USERNAME_NOT_EXIST.description);
        }catch (AuthenticationException e){
            log.error(userName+"密码错误");
            return ResultData.error(Error.USER_PASSWORD_FAIL.code,Error.USER_PASSWORD_FAIL.description);
        }
        return ResultData.success(Success.USER_LOGIN_SUCCESS.code,Success.USER_LOGIN_SUCCESS.description);
    }

    /**
     * 注册
     * @param memberDto
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/add/user")
    public ResultData addUser(@RequestBody @NotNull TemplateMemberDto memberDto){

        if (StringUtils.isEmpty(memberDto.getLoginName())){
            return ResultData.error(Error.USER_NOT_LOGIN_NULL.code,Error.USER_NOT_LOGIN_NULL.description);
        }

        if (StringUtils.isEmpty(memberDto.getLoginPassword())){
            return ResultData.error(Error.USER_NOT_PASSWORD_NULL.code,Error.USER_NOT_PASSWORD_NULL.description);
        }

        TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo(memberDto.getLoginName()).setLimit(1L));
        if (member != null){
            return ResultData.error(Error.USERNAME_EXIST.code,Error.USERNAME_EXIST.description);
        }
        //加密
        String loginPassword = memberDto.getLoginPassword();
        loginPassword = DigestUtils.Md5(memberDto.getLoginName(),loginPassword);
        memberDto.setLoginPassword(loginPassword);
        memberDto.setCreateTime(new Date());
        memberDto.setUpdateTime(new Date());

        int result = templateMemberDao.insert(memberDto);
        if(result == 0 ){
            return ResultData.error(Error.USER_REGISTER_FAIL.code,Error.USER_REGISTER_FAIL.description);
        }

        return ResultData.success(Success.USER_REGISTER_SUCCESS.code,Success.USER_REGISTER_SUCCESS.description);
    }

    /**
     * 修改
     * @param memberDto
     * @return
     */
    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/motify/user")
    public String motifyUser(@RequestBody @NotNull TemplateMemberDto memberDto){

        if (StringUtils.isEmpty(memberDto.getLoginName())){
            return "login name is empty!";
        }

        if (StringUtils.isEmpty(memberDto.getLoginPassword())){
            return "login password is empty!";
        }

        if (StringUtils.isEmpty(memberDto.getNewPassword())){
            return "new password is empty!";
        }

        TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo(memberDto.getLoginName()).setLimit(1L));
        if (member == null){
            return "login name is not exist!";
        }

        if (!member.getLoginPassword().equals(memberDto.getLoginName())){
            return "original password is wrong!";
        }

        member.setLoginPassword(memberDto.getNewPassword());
        member.setUpdateTime(new Date());

        templateMemberDao.update(member);

        return "motify password success";
    }

}
