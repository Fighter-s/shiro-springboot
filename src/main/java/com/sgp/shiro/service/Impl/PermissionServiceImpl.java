package com.sgp.shiro.service.Impl;

import com.sgp.shiro.entity.Permission;
import com.sgp.shiro.mapper.PermissionMapper;
import com.sgp.shiro.service.IPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private PermissionMapper permissionMapper;


    @Override
    public Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet) {
        return permissionMapper.findPermsByRoleIdIn(roleIdSet);
    }


}
