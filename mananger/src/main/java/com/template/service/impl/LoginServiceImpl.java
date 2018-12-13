package com.template.service.impl;

import com.template.base.dao.PermissionDao;
import com.template.base.dao.RoleDao;
import com.template.base.dao.UserDao;
import com.template.base.domain.Permission;
import com.template.base.domain.Role;
import com.template.base.domain.User;
import com.template.base.domain.criteria.PermissionCriteria;
import com.template.base.dto.LoginDto;
import com.template.base.dto.PermDto;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: LoginServiceImpl
 * @Description:
 * @author: youqing
 * @versio: 1.0
 * @date: 2018/12/12 19:08
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

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
            //data.put("token",user);
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


    @Override
    public ResultData getUserPerms(User user) {
        Map<String, Object> data = new HashMap<>();
        //Integer roleId = user.getRoleId();
        Role role = roleDao.selectOne(1);
        if (null != role ) {
            String permissions = role.getPermissions();

            String[] ids = permissions.split(",");
            List<PermDto> permissionList = new ArrayList<>();
            for (String id : ids) {
                // 角色对应的权限数据
                Permission perm = permissionDao.selectOne(Integer.parseInt(id));
                if (null != perm ) {
                    // 授权角色下所有权限
                    PermDto permDto = new PermDto();
                    BeanUtils.copyProperties(perm,permDto);
                    //获取子权限
                    List<Permission> chils = permissionDao.selectList(PermissionCriteria.pidEqualTo(perm.getId()));
                    List<PermDto> childrens = new ArrayList <>();
                    for(Permission  p : chils){
                        PermDto permDto1 = new PermDto();
                        BeanUtils.copyProperties(p,permDto1);
                        childrens.add(permDto1);
                    }
                    permDto.setChildrens(childrens);
                    permissionList.add(permDto);
                }
            }
            data.put("perms",permissionList);
        }
        return ResultData.success("","").with(data);
    }

}
