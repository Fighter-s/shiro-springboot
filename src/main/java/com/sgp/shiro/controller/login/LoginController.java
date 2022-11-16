package com.sgp.shiro.controller.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public String login(String username, String password,String rememberMe) {
        Subject subject = SecurityUtils.getSubject();

        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(rememberMe != null && "on".equals(rememberMe));
            subject.login(token);
            return "seccess";
        } catch (UnknownAccountException e) {
            return "account error";
        } catch (IncorrectCredentialsException e) {
            return "password error";
        } catch (AuthenticationException e) {
            return "unknown error";
        }

    }
}
