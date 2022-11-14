package com.sgp.shiro.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 映射用户表
 * @author zjw
 * @description
 */
@Data
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String salt;

}
