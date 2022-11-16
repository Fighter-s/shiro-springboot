package com.sgp.shiro.realm;

import com.alibaba.druid.util.StringUtils;
import com.sgp.shiro.entity.Permission;
import com.sgp.shiro.entity.Role;
import com.sgp.shiro.entity.User;
import com.sgp.shiro.service.IPermissionService;
import com.sgp.shiro.service.IRoleService;
import com.sgp.shiro.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShiroRealm extends AuthorizingRealm {

    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        this.setCredentialsMatcher(matcher);
    }

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userName = (String)authenticationToken.getPrincipal();
        // 判断用户名是否为空
        if(StringUtils.isEmpty(userName)){
            return null;
        }
        // 使用用户名查询数据库
        User user = userService.findByUsername(userName);
        // 判断是否存在用户
        if(user == null){
            return null;
        }
        // 将用户的密码交给shiro判断密码是否正确
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(),"customerRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));

        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        if(subject == null) return null;
        if(!subject.isAuthenticated()) return null;
        // 获取用户对象信息
        User user = (User)principalCollection.getPrimaryPrincipal();
        // 获取用户存在的角色信息
        Set<Role> roles = roleService.findRolesByUid(user.getId());

        Set<Integer> ids = roles.stream().map(Role::getId).collect(Collectors.toSet());
        Set<String> names = roles.stream().map(Role::getName).collect(Collectors.toSet());

        // 获取用户每个角色存在的权限
        Set<Permission> permsByRoleSet = permissionService.findPermsByRoleSet(ids);
        Set<String> permsSet = permsByRoleSet.stream().map(Permission::getName).collect(Collectors.toSet());

        // 将用户的角色和权限都交给shiro处理
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(names);
        info.setStringPermissions(permsSet);
        info.setRoles(names);
        // 获取用户名

        return info;
    }


}
