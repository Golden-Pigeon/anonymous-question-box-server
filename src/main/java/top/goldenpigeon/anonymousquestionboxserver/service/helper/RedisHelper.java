package top.goldenpigeon.anonymousquestionboxserver.service.helper;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisHelper {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }


    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }


    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void deleteByKey(String key) {
        stringRedisTemplate.delete(key);
    }
}
