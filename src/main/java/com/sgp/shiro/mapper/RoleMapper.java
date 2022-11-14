package com.sgp.shiro.mapper;

import com.sgp.shiro.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author zjw
 * @description
 */
public interface RoleMapper {

    Set<Role> findRolesByUid(@Param("uid") Integer uid);

}
