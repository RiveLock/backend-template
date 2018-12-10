package com.template.shiro;


import com.template.base.dao.TemplateMemberDao;
import com.template.base.domain.TemplateMember;
import com.template.base.domain.criteria.TemplateMemberCriteria;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: MyRealm
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:10
 */
public class MyRealm extends AuthorizingRealm{

   private Logger logger = LoggerFactory.getLogger(MyRealm.class);
   @Autowired
   private TemplateMemberDao templateMemberDao;

    /**
     *
     * 功能描述: 授权
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/9/11 10:30
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        //授权
        logger.info("授予角色和权限");
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        return authorizationInfo;
    }


    /**
     *
     * 功能描述: 认证
     *
     * @param:
     * @return:
     * @auther: youqing
     * @date: 2018/9/11 10:32
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("用户登录认证：验证当前Subject时获取到token为：" + token);
        String username = token.getUsername();
        // 调用数据层
        TemplateMember member = templateMemberDao.selectOne(TemplateMemberCriteria.loginNameEqualTo(token.getUsername()).setLimit(1L));

        logger.info("用户登录认证！用户信息member：" + member);
        if (member == null) {
            // 用户不存在
            return null;
        }
        // 返回密码
        return new SimpleAuthenticationInfo(member, member.getLoginPassword(), ByteSource.Util.bytes(username), getName());
    }
}
