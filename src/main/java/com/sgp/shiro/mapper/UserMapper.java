package com.sgp.shiro.mapper;

import com.sgp.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zjw
 * @description
 */
public interface UserMapper {
    User findUserByUsername(@Param("username") String username);

}
