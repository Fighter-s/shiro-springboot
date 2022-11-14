package com.sgp.shiro.service;

import com.sgp.shiro.entity.Permission;
import com.sgp.shiro.entity.User;

import java.util.Set;

public interface IUserService {

    User findByUsername(String username);
}
