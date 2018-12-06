package com.template.demo.controller;

import com.template.demo.base.dao.TemplateMemberDao;
import com.template.demo.base.domain.TemplateMember;
import com.template.demo.base.domain.criteria.TemplateMemberCriteria;
import com.template.demo.base.dto.TemplateMemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created jixinshi on 2018-11-27.
 */
@Api("用户信息 接口")
@RequestMapping(value = "/member")
@RestController
public class MemberController {

    @Resource
    private TemplateMemberDao templateMemberDao;

    @GetMapping(value = "/user")
    public String getUser(){
        return "lalala";
    }

    /**
     * 登陆
     * @param memberDto
     * @return
     */
    @ApiOperation(value = "登陆用户")
    @PostMapping(value = "/login/user")
    public String loginUser(@RequestBody @NotNull TemplateMemberDto memberDto){

        if (StringUtils.isEmpty(memberDto.getLoginName())){
            return "login name is empty!";
        }

        if (StringUtils.isEmpty(memberDto.getLoginPassword())){
            return "login password is empty!";
        }

        TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo(memberDto.getLoginName()).setLimit(1L));
        if (member == null){
            return "login name is wrong!";
        }
        if (!member.getLoginPassword().equals(memberDto.getLoginPassword())){
            return "login passwor is wrong!";
        }
        return "login success";
    }

    /**
     * 注册
     * @param memberDto
     * @return
     */
    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/add/user")
    public String addUser(@RequestBody @NotNull TemplateMemberDto memberDto){

        if (StringUtils.isEmpty(memberDto.getLoginName())){
            return "login name is empty!";
        }

        if (StringUtils.isEmpty(memberDto.getLoginPassword())){
            return "login password is empty!";
        }

        TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo(memberDto.getLoginName()).setLimit(1L));
        if (member != null){
            return "login name is exist!";
        }


        memberDto.setCreateTime(new Date());
        memberDto.setUpdateTime(new Date());


        templateMemberDao.insert(memberDto);

        return "register success";
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
