package com.love.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        if (redisTemplate == null) {
            throw new NullPointerException("!!! redisTemplate is null !!!");
        }
    }

    /**
     * @param prefix 前缀
     * @return set
     */
    public Set<String> keys(String prefix) {
        return redisTemplate.keys(prefix);
    }


    /**
     * 使用scan替代keys
     *
     * @param pattern 匹配模式
     * @param limit   条数
     * @return keys
     */
    public Set<String> scan(String pattern, int limit) {
        return redisTemplate.execute(( RedisCallback<Set<String>> ) connection -> {
            Set<String> keys = new HashSet<>();
            ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
            try (Cursor<byte[]> cursor = connection.scan(options)) {
                while (cursor.hasNext()) {
                    keys.add(new String(cursor.next(), StandardCharsets.UTF_8));
                }
            }
            return keys;
        });
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                Boolean ret = redisTemplate.expire(key, time, TimeUnit.SECONDS);
                return ret != null && ret;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        Long ret = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return ret == null ? 0 : ret;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean ret = redisTemplate.hasKey(key);
            return ret != null && ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    public boolean del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                Boolean ret = redisTemplate.delete(keys[0]);
                return ret != null && ret;
            } else {
                Long ret = redisTemplate.delete(Arrays.asList(keys));
                return ret != null && ret == keys.length;
            }
        }
        return false;
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取多个
     *
     * @param keys 键
     * @return result
     */
    public List<Object> mget(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 递增
     *
     * @param key   key
     * @param delta step
     * @return result
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        Long ret = redisTemplate.opsForValue().increment(key, delta);
        return ret == null ? 0 : ret;
    }

    /**
     * 递减
     *
     * @param key   key
     * @param delta step
     * @return result
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        Long ret = redisTemplate.opsForValue().increment(key, -delta);
        return ret == null ? 0 : ret;
    }


    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }


    /**
     * HashGetALl
     *
     * @param key 键 不能为null
     * @return 值
     */
    public Object hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return result
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return result
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return result
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean ret = redisTemplate.opsForSet().isMember(key, value);
            return ret != null && ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            Long ret = redisTemplate.opsForSet().add(key, values);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (count != null && count > 0 && time > 0) {
                expire(key, time);
            }
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return result
     */
    public long sSize(String key) {
        try {
            Long ret = redisTemplate.opsForSet().size(key);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sRemove(String key, Object... values) {
        try {
            Long ret = redisTemplate.opsForSet().remove(key, values);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return result
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return result
     */
    public long lSize(String key) {
        try {
            Long ret = redisTemplate.opsForList().size(key);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return result
     */
    public Object lGet(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将list放入缓存
     *
     * @param key   key
     * @param value value
     * @return result
     */
    public long lSet(String key, Object value) {
        try {
            Long count = redisTemplate.opsForList().rightPush(key, value);
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return result
     */
    public long lSet(String key, Object value, long time) {
        try {
            Long count = redisTemplate.opsForList().rightPush(key, value);
            if (count != null && count > 0 && time > 0) {
                expire(key, time);
            }
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return result
     */
    public long lSet(String key, List<Object> value) {
        try {
            Long count = redisTemplate.opsForList().rightPushAll(key, value);
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return result
     */
    public long lSet(String key, List<Object> value, long time) {
        try {
            Long count = redisTemplate.opsForList().rightPushAll(key, value);
            if (count != null && count > 0 && time > 0) {
                expire(key, time);
            }
            return count == null ? 0 : count;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return result
     */
    public boolean lSet(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long ret = redisTemplate.opsForList().remove(key, count, value);
            return ret == null ? 0 : ret;
        } catch (Exception e) {
            logger.error("", e);
        }
        return 0;
    }
}
