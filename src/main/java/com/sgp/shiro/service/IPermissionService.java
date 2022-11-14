package com.sgp.shiro.service;

import com.sgp.shiro.entity.Permission;

import java.util.Set;

public interface IPermissionService {
    Set<Permission> findPermsByRoleSet(Set<Integer> roleIdSet);
}
