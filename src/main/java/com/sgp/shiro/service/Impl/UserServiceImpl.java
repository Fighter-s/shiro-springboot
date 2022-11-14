package com.sgp.shiro.service.Impl;

import com.sgp.shiro.entity.Permission;
import com.sgp.shiro.entity.User;
import com.sgp.shiro.mapper.PermissionMapper;
import com.sgp.shiro.mapper.UserMapper;
import com.sgp.shiro.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
