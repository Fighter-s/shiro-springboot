package com.sgp.shiro.service;

import com.sgp.shiro.entity.Role;

import java.util.Set;

public interface IRoleService {

    Set<Role> findRolesByUid(Integer uid);
}
