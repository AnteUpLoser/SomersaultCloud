package com.common.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 *  封装stringRedisTemplate底层方法
 * 易于自己使用
 */

@Slf4j
@Service
public class RedisServiceImpl implements RedisService{
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //获取对应key的value值
    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    //设置持久化kv
    public void setValue(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    //删除kv
    public void deleteValue(String key){
        stringRedisTemplate.delete(key);
    }

    //判断是否存在kv
    public boolean isContainsKey(String key){
        Boolean result = stringRedisTemplate.hasKey(key);
        return result != null && result;
    }

    //设置一分钟有效期的值
    public void setOneMinValue(String key, String value){
        stringRedisTemplate.opsForValue().set(key,value,1, TimeUnit.MINUTES);
    }

    //设置键值对并设置单位为分钟的时间
    public void setValueByMin(String key, String value, int min) {
        stringRedisTemplate.opsForValue().set(key,value,min,TimeUnit.MINUTES);
    }

    //设置半小时有效期的值
    public void setHalfHourValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value,30,TimeUnit.MINUTES);
    }

    //存储聊天记录
    public void storeChatMessage(String key, String message, long timestamp){
        stringRedisTemplate.opsForZSet().add(key, message, timestamp);
    }

    //获取所有聊天记录
    public Set<String> getChatMessages(String key) {
        return stringRedisTemplate.opsForZSet().reverseRange(key, 0, -1);
    }




}
