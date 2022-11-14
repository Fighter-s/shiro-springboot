package com.sgp.shiro.service.Impl;

import com.sgp.shiro.entity.Role;
import com.sgp.shiro.mapper.RoleMapper;
import com.sgp.shiro.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private RoleMapper roleMapper;


    @Override
    public Set<Role> findRolesByUid(Integer uid) {
        return roleMapper.findRolesByUid(uid);
    }
}
