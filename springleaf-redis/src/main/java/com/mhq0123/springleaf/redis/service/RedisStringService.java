package com.mhq0123.springleaf.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName RedisStringService
 * @date 2016-07-21
 * @memo
 */
@Component
public class RedisStringService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(final String key, final String value) {
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                ((StringRedisConnection)connection).set(key, value);
                return null;
            }
        });
    }

    public String get(final String key) {
        return stringRedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return ((StringRedisConnection)connection).get(key);
            }
        });
    }

    public void delete(final String key) {
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                ((StringRedisConnection)connection).del(key);
                return null;
            }
        });
    }
}
