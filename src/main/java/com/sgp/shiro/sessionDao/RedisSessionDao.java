package com.sgp.shiro.sessionDao;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private final static String SESSION_WEB = "session_web_";

    @Override
    protected Serializable doCreate(Session session) {
        if(session == null) return null;
        log.info("创建session");
        // 获取sessionId
        Serializable sessionId = this.generateSessionId(session);
        // 将sessionId和session绑定
        this.assignSessionId(session, sessionId);
        // 将sessionId和session存储到redis中
        redisTemplate.opsForValue().set(SESSION_WEB+sessionId,session,30, TimeUnit.MINUTES);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("读取session");
        if (sessionId == null) {
            return null;
        }

        // redis中获取session
        Session session = (Session) redisTemplate.opsForValue().get(SESSION_WEB + sessionId);

        if (session == null) {
            return null;
        }

        redisTemplate.expire(SESSION_WEB+sessionId,30,TimeUnit.MINUTES);

        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("修改session");
        if(session == null){
            return;
        }
        // 获取sessionID
        redisTemplate.opsForValue().set(SESSION_WEB+session.getId(),session,30,TimeUnit.MINUTES);
    }

    @Override
    public void delete(Session session) {
        log.info("删除session");
        if(session == null){
            return;
        }

        redisTemplate.delete(SESSION_WEB+session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessionSet = new HashSet<>();
        // 获取所有的key
        Set<String> keys = redisTemplate.keys(SESSION_WEB + "*");
        // 根据所有的key获取所有的session
        for (String key : keys) {
            Session session =(Session) redisTemplate.opsForValue().get(SESSION_WEB + key);
            sessionSet.add(session);
        }

        return sessionSet;
    }
}
