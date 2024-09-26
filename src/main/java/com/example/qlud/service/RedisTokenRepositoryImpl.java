package com.example.qlud.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    private final RedisTemplate<Object, Object> redisTemplate;
    private static final String REMEMBER_ME_KEY_PREFIX="spring:security:rememberMe:";

    public RedisTokenRepositoryImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String key = REMEMBER_ME_KEY_PREFIX + token.getSeries();
        redisTemplate.opsForValue().set(key, token, 7 , TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = getTokenForSeries(series);
        if ((token != null)){
            createNewToken(new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = REMEMBER_ME_KEY_PREFIX + seriesId;
        return (PersistentRememberMeToken) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void removeUserTokens(String username) {
        redisTemplate.delete(REMEMBER_ME_KEY_PREFIX + username);
    }
}
