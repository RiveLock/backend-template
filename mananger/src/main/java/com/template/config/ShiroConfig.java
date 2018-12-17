package com.template.config;


import com.template.filter.UserFormAuthenticationFilter;
import com.template.shiro.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: ShiroConfig
 * @Description: 安全框架shiro配置类
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/7 16:33
 */
@Configuration
public class ShiroConfig {




    /**
     * 1.创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 关联 SecurityManager
        bean.setSecurityManager(securityManager);

        Map<String, String> filterMap = new LinkedHashMap<>();

        //放行登录口和退出系统的接口
        filterMap.put("/mananger/login", "anon");
        filterMap.put("/mananger/logout", "anon");
        /*放行swagger*/
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/webjars/springfox-swagger-ui/**", "anon");

        bean.setLoginUrl("/mananger/unauth");

        // 把自定义Filter添加到shiro过滤器列表
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("userFilter", userFormAuthenticationFilter());

        bean.setFilters(filters);

        // 添加 shiro 过滤器
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    // 创建自定义的过滤器
    @Bean(name = "userFilter")
    public UserFormAuthenticationFilter userFormAuthenticationFilter() {
        return new UserFormAuthenticationFilter();
    }

    /**
     * 2.创建SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        // 关联realm
        manager.setRealm(myReal());

        return manager;
    }



    /**
     * 3.创建 Realm
     */
    @Bean
    public MyRealm myReal() {
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码,更改密码生成规则和校验的逻辑一致即可; ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于 // md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }



}
