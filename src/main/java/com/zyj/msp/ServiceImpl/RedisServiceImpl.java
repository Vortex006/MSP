package com.zyj.msp.ServiceImpl;

import com.zyj.msp.Service.RedisService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redis;

    @Autowired
    public RedisServiceImpl(RedisTemplate<String, Object> redis) {
        this.redis = redis;
    }

    @Override
    public Boolean setString(String key, Object value) {
        redis.opsForValue().set(key, value);
        Boolean hasKey = hasKey(key);
        return hasKey;
    }

    @Override
    public Boolean setString(String key, Object value, long timeout, TimeUnit unit) {
        redis.opsForValue().set(key, value, timeout, unit);
        Boolean hasKey = hasKey(key);
        return hasKey;
    }

    @Override
    public Boolean setHash(String key, String field, Object value) {
        try {
            redis.opsForHash().put(key, field, value);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }


    @Override
    public Boolean setHash(String key, HashMap<String, Object> map) {
        try {
            redis.opsForHash().putAll(key, map);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean setList(String key, Object value) {
        Long size = getListSize(key);
        Long newSize = redis.opsForList().rightPush(key, value);
        Boolean isSuccess = Objects.equals(size + 1, newSize);
        return isSuccess;
    }

    @Override
    public Boolean setList(String key, List<Object> list) {
        Long size = getListSize(key);
        Long newSize = redis.opsForList().rightPushAll(key, list);
        Boolean isSuccess = Objects.equals(size + list.size(), newSize);
        return isSuccess;
    }

    @Override
    public Boolean setListLeft(String key, Object value) {
        Long size = getListSize(key);
        Long newSize = redis.opsForList().leftPush(key, value);
        Boolean isSuccess = Objects.equals(size + 1, newSize);
        return isSuccess;
    }

    @Override
    public Boolean setListLeft(String key, List<Object> list) {
        Long size = getListSize(key);
        Long newSize = redis.opsForList().leftPushAll(key, list);
        Boolean isSuccess = Objects.equals(size + list.size(), newSize);
        return isSuccess;
    }

    @Override
    public Object getString(String key) {
        String value = String.valueOf(redis.opsForValue().get(key));
        return value;
    }

    @Override
    public Object getHash(String key, String field) {
        String value = String.valueOf(redis.opsForHash().get(key, field));
        return value;
    }

    @Override
    public HashMap<String, Object> getHash(String key) {
        Map<Object, Object> map = redis.opsForHash().entries(key);
        HashMap<String, Object> hashMap = changeMap(map);
        return hashMap;
    }

    @Override
    public List<Object> getList(String key, long start, long end) {
        List<Object> list = redis.opsForList().range(key, start, end);
        return list;
    }

    @Override
    public Long getListSize(String key) {
        Long size = redis.opsForList().size(key);
        return size;
    }

    @Override
    public Boolean delete(String key) {
        Boolean isDelete = redis.delete(key);
        return isDelete;
    }

    @Override
    public Boolean delete(List<String> keys) {
        Long deleteKeys = redis.delete(keys);
        Boolean isDelete = Objects.equals(deleteKeys, (long) keys.size());
        return isDelete;
    }

    @Override
    public Boolean expireMilliSeconds(String key, long time) {
        Boolean isSuccess = redis.expire(key, time, TimeUnit.MILLISECONDS);
        return isSuccess;
    }

    @Override
    public Boolean expireSeconds(String key, long time) {
        Boolean isSuccess = redis.expire(key, time, TimeUnit.SECONDS);
        return isSuccess;
    }

    @Override
    public Boolean expireMinutes(String key, long time) {
        Boolean isSuccess = redis.expire(key, time, TimeUnit.MINUTES);
        return isSuccess;
    }

    @Override
    public Boolean expireHours(String key, long time) {
        Boolean isSuccess = redis.expire(key, time, TimeUnit.HOURS);
        return isSuccess;
    }

    @Override
    public Boolean expireDays(String key, long time) {
        Boolean isSuccess = redis.expire(key, time, TimeUnit.DAYS);
        return isSuccess;
    }

    @Override
    public Boolean expire(String key, long time) {
        Boolean isSuccess = expireSeconds(key, time);
        return isSuccess;
    }

    @Override
    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        Boolean isSuccess = redis.expire(key, time, timeUnit);
        return isSuccess;
    }

    @Override
    public Boolean expire(String key, LocalDate date) {
        Boolean isSuccess = redis.expireAt(key, Instant.from(date));
        return isSuccess;
    }

    @Override
    public Long getExpire(String key) {
        Long expire = redis.getExpire(key);
        return expire;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean isHasKey = redis.hasKey(key);
        return isHasKey;
    }

    @Override
    public Long valueAdd(String key, long delta) {
        Long value = redis.opsForValue().increment(key, delta);
        return value;
    }

    @Override
    public Long valueRemove(String key, long delta) {
        Long value = redis.opsForValue().decrement(key, delta);
        return value;
    }

    @Override
    public HashMap<String, Object> changeMap(Map<Object, Object> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            hashMap.put(entry.getKey().toString(), entry.getValue());
        }
        return hashMap;
    }

    @Override
    public Boolean equals(String key, Object value) {
        Object oldValue = getString(key);
        return Objects.equals(oldValue, value);
    }

    @Override
    public T execute(RedisCallback<T> action) {
        T execute = redis.execute(action);
        return execute;
    }


}
