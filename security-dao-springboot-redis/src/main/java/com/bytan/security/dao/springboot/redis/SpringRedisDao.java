package com.bytan.security.dao.springboot.redis;

import com.bytan.security.core.data.dao.SecurityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 基于springboot启动器实现框架dao层对redis的集成使用
 * @Author: ByTan
 * @Eamil: tx1611235218@gmail.com
 * @Date: 2024/12/24  23:45
 */
public class SpringRedisDao implements SecurityDao {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SpringRedisDao(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory connectionFactory) {
        if (Objects.isNull(connectionFactory)) {
            throw new NullPointerException("redis连接信息为空");
        }
        if (Objects.isNull(redisTemplate)) {
            JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();

            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            // 使用StringRedisSerializer来序列化和反序列化redis的key值
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(serializer);
            // Hash的key也采用StringRedisSerializer的序列化方式
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(serializer);
            template.afterPropertiesSet();

            this.redisTemplate = template;
        } else {
            this.redisTemplate = redisTemplate;
        }
    }

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void saveBatchByMap(String key, Map<String, Object> objMap) {
        redisTemplate.opsForHash().putAll(key, objMap);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deleteByMap(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public void update(String key, Object value) {
        save(key, value);
    }

    @Override
    public void updateBatchByMap(String key, Map<String, Object> objMap) {
        saveBatchByMap(key, objMap);
    }

    @SuppressWarnings(value = { "unchecked" })
    @Override
    public <T> T get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : (T) value;
    }

    @SuppressWarnings(value = { "unchecked" })
    @Override
    public <T> T getByMap(String key, String field) {
        Object value = redisTemplate.opsForHash().get(key, field);
        return value == null ? null : (T) value;
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        if (timeout != SecurityDao.NOT_EXPIRE) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
    }
}
