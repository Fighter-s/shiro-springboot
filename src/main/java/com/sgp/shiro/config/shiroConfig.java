package com.sgp.shiro.config;

import com.sgp.shiro.realm.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

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


    @Bean
    public DefaultShiroFilterChainDefinition filterChainDefinition(){
         Map<String, String> filterChainDefinitionMap = new LinkedHashMap();
        DefaultShiroFilterChainDefinition filterChainDefinition = new DefaultShiroFilterChainDefinition();
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/user/logout","logout");
        filterChainDefinitionMap.put("/user/**","anon");
        filterChainDefinitionMap.put("/**","authc");

        filterChainDefinition.addPathDefinitions(filterChainDefinitionMap);

        return filterChainDefinition;
    }
}
