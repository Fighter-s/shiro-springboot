package com.sgp.shiro.config;

import com.sgp.shiro.filter.RolesOrAuthorizationFilter;
import com.sgp.shiro.realm.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);

        return securityManager;
    }

    /**
     * 过滤器链  DefaultFilter
     * @return
     */
    @Bean
    public DefaultShiroFilterChainDefinition filterChainDefinition(){
         Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
        DefaultShiroFilterChainDefinition filterChainDefinition = new DefaultShiroFilterChainDefinition();
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/user/logout","logout");
        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/item/update","user");
        filterChainDefinitionMap.put("/item/**","rolesOr[admin,user]");
        filterChainDefinitionMap.put("/item/delte","perms[item:add]");
        filterChainDefinitionMap.put("/**","authc");

        filterChainDefinition.addPathDefinitions(filterChainDefinitionMap);


        return filterChainDefinition;
    }

    @Autowired
    protected Map<String, Filter> filterMap;
    @Value("#{ @environment['shiro.loginUrl'] ?: '/login.jsp' }")
    protected String loginUrl;
    @Value("#{ @environment['shiro.successUrl'] ?: '/' }")
    protected String successUrl;
    @Value("#{ @environment['shiro.unauthorizedUrl'] ?: null }")
    protected String unauthorizedUrl;

    /**
     * 自定义shiroFilterFactoryBean
     * @param securityManager
     * @param shiroFilterChainDefinition
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setLoginUrl(this.loginUrl);
        filterFactoryBean.setSuccessUrl(this.successUrl);
        filterFactoryBean.setUnauthorizedUrl(this.unauthorizedUrl);
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());
        filterFactoryBean.setFilters(this.filterMap);

        // 添加自定义过滤器
        filterFactoryBean.getFilters().put("rolesOr", new RolesOrAuthorizationFilter());
        return filterFactoryBean;
    }

}
