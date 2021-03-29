package com.xuhu.springboodemo.core.service.impl;

import com.xuhu.springboodemo.core.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component(value = "redisService")
//@Slf4j
public class RedisServiceImpl implements CacheService {

    private  final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set<String> getKeys(String... pattern) {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < pattern.length; i++) {
            Set<String> allKeys = redisTemplate.keys(pattern[i]);
            if (null == allKeys || allKeys.isEmpty()) {
                continue;
            }
            keys.addAll(allKeys);
        }
        return keys;
    }

    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置缓存失效时间异常=" + key, e);
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("判断key是否存在异常=" + key, e);
            return false;
        }
    }

    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    @Override
    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis获取value异常，key:[{}]", key);
            return null;
        } finally {
//            TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
        }
    }

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("普通缓存放入错误=" + key, e);
            return false;
        }

    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("普通缓存放入并设置超期时间错误 =" + key, e);
            return false;
        } finally {
//            TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
        }
    }

    /**
     * 批量往redis中设置多个键值对
     *
     * @param map map
     */
    @Override
    public void mSet(Map<String, Object> map) {
        if (null == map || map.isEmpty()) {
            return;
        }
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public List<Object> hmget(String key, Collection<String> hashKeys) {
        if (hashKeys == null || hashKeys.isEmpty()) {
            return Collections.emptyList();
        }

        return redisTemplate.opsForHash().multiGet(key, new ArrayList<>(hashKeys));
    }

    @Override
    public Map<Object, Object> hmgetForKV(String key, Collection<String> hashKeys) {
        if (hashKeys == null || hashKeys.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Object> hashKeyList = hashKeys.stream().collect(Collectors.toList());
        List<Object> valueList = redisTemplate.opsForHash().multiGet(key, hashKeyList);
        int size = hashKeyList.size();
        Map<Object, Object> kvMap = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            Object value = valueList.get(i);
            if (value == null) {
                continue;
            }
            kvMap.put(hashKeyList.get(i), value);
        }

        return kvMap;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("HashSet对象缓存错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("HashSet对象缓存并设置缓存时间错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("向hash表中放入数据,如果不存在将创建错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("向hash表中放入数据并设置失效时间,如果不存在将创建错误 =" + key, e);
            return false;
        }
    }

    @Override
    public void hdel(String key, String... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    @Override
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    @Override
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("根据key获取Set中的所有值错误 =" + key, e);
            return null;
        }
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("根据value从set中查询,是否存在错误 =" + key, e);
            return false;
        }
    }

    @Override
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("set缓存对象错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public boolean zSet(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return false;
        }
    }

    @Override
    public Long batchZSet(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        if (set == null || set.isEmpty()) {
            return 0L;
        }

        try {
            return redisTemplate.opsForZSet().add(key, set);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return 0L;
        }
    }

    @Override
    public Set<Object> getZSet(String key, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, offset, count);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    /**
     * 获取zSet值 by score
     *
     * @param key        key
     * @param scoreBegin scoreBegin
     * @param scoreEnd   scoreEnd
     * @param offset     offset
     * @param count      count
     * @return set
     */
    @Override
    public Set<Object> getZSetByScore(String key, double scoreBegin, double scoreEnd, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, scoreBegin, scoreEnd, offset, count);
        } catch (Exception e) {
            log.error("获取zSet缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Object>> getZSetWithScore(String key, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, offset, count);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    @Override
    public Long getZSetCount(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return 0L;
        }
    }

    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("set缓存对象并设置缓存时间错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取set缓存的长度错误 =" + key, e);
            return 0;
        }
    }

    public long setRemove(String key) {
        try {
            long count = 0;
            Set<Object> objs = sGet(key);
            for (Object object : objs) {
                count += redisTemplate.opsForSet().remove(key, object);
            }
            return count;
        } catch (Exception e) {
            log.error("删除key的values值错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("删除key的values值错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public long zsetRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForZSet().remove(key, values);
            return count;
        } catch (Exception e) {
            log.error("删除key的values值错误 =" + key, e);
            return 0;
        }
    }

    //===============================list=================================
    @Override
    public List<Object> getAllList(String key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            log.error("获取list缓存的内容错误 =" + key, e);
            return null;
        }
    }

    @Override
    public List<Object> lGet(String key) {
        try {
            return lGet(key, 0);
        } catch (Exception e) {
            log.error("获取list缓存的内容错误 =" + key, e);
            return null;
        }
    }

    @Override
    public List<Object> lGet(String key, long start) {
        try {
            return lGet(key, start, lGetListSize(key));
        } catch (Exception e) {
            log.error("获取list缓存的内容错误 =" + key, e);
            return null;
        }
    }

    @Override
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("获取list缓存的内容错误 =" + key, e);
            return null;
        }
    }

    @Override
    public long lGetListSize(String key) {
        try {
            Long l = redisTemplate.opsForList().size(key);
            if (null == l) {
                return 0;
            }
            return l;
        } catch (Exception e) {
            log.error("获取list缓存的长度错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("通过索引 获取list中的值错误 =" + key, e);
            return null;
        }
    }

    @Override
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("list放入缓存错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("list放入缓存并设置缓存时间错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(List<Object> value, String key) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("list多个对象放入缓存错误 =" + key, e);
            return false;
        }
    }

    /**
     * 将obj 放入redisList中 （右边插入）
     *
     * @param key   key
     * @param value 待插入value
     * @return 是否成功
     */
    @Override
    public boolean lRightSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("list多个对象放入缓存错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean lRightSetAll(String key, Collection<?> values) {
        if (values == null || values.isEmpty()) {
            return true;
        }
        try {
            redisTemplate.opsForList().rightPushAll(key, values.toArray());
            return true;
        } catch (Exception e) {
            log.error("list多个对象放入缓存错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean leftPushAll(List<Object> value, String key) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value.toArray());
            return true;
        } catch (Exception e) {
            log.error("list多个对象放入缓存错误 =" + key, e);
            return false;
        }
    }

    @Override
    public boolean lSet(List<Object> value, String key, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("list多个对象放入缓存并设置缓存时间错误 =" + key, e);
            return false;
        }
    }


    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("根据索引修改list中的某条数据错误 =" + key, e);
            return false;
        }
    }

    public long lRemove(String... key) {
        long remove = 0;
        try {
            for (int i = 0; i < key.length; i++) {
                for (int j = 0; j < lGetListSize(key[i]); j++, remove++) {
                    List<Object> objs = redisTemplate.opsForList().range(key[i], j, j);
                    lRemove(key[i], objs.size(), objs.get(0));
                }
            }
            return remove;
        } catch (Exception e) {
            log.error("删除多个缓存对象错误 =" + key, e);
            return remove;
        }
    }

    @Override
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("删除多个缓存对象错误 =" + key, e);
            return 0;
        }
    }

    @Override
    public boolean setnx(String key, String expiresStr) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, expiresStr);
        } catch (Exception e) {
            log.error("setnx错误 =" + key, e);
            return false;
        }
    }

    @Override
    public String getSet(String key, String expiresStr) {
        try {
            return redisTemplate.opsForValue().getAndSet(key, expiresStr).toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getSet缓存对象错误 =" + key, e);
            return null;
        }
    }

    @Override
    public boolean lSet(String key, String value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("list左放入缓存错误 key=[{}],e:[{}] ", key, e);
            return false;
        }
    }

    @Override
    public Object rGet(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("list右取错误 key=[{}],e:[{}] ", key, e);
            return "";
        }
    }

    @Override
    public Boolean exists(String key) {
        try {
            return key == null ? false : redisTemplate.hasKey(key);
        } catch (Exception e) {
            return null;
        } finally {
        }
    }

    @Override
    public void lTrim(String key, Long start, Long stop) {
        try {
            redisTemplate.opsForList().trim(key, start, stop);
        } catch (Exception e) {
            log.error("list裁剪错误 key=[{}],e:[{}] ", key, e);
        }
    }

    /**
     * 功能描述: 倒序查询指定key的zSet数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author zyd
     * @date 2020/5/16 17:02
     */
    @Override
    public Set<Object> reverseGetAllZSet(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    /**
     * 正序获取指定key的zet数据
     *
     * @param key    key
     * @param offset offset
     * @param count  count  -1表示获取全部
     * @return Set<Object>
     */
    @Override
    public Set<Object> getPositiveZSet(String key, long offset, long count) {
        try {
            return redisTemplate.opsForZSet().range(key, offset, count);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    /**
     * 功能描述: 倒序查询指定key的带score的zSet数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author zyd
     * @date 2020/5/16 17:02
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return new HashSet<>();
        }
    }

    /**
     * 功能描述: 查询zSet某个member的分数
     *
     * @param key
     * @return
     * @author zyd
     * @date 2020/5/23 10:09
     */
    @Override
    public Double zSetGetScore(String key, String member) {
        try {
            return redisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            log.error("zset缓存对象错误 =" + key, e);
            return null;
        }
    }

    /**
     * 功能描述: 删除zSet中指定排位区间的元素，分数最低区间为0
     *
     * @param key
     * @param start 起始区间
     * @param end   结束区间
     * @return
     * @author zyd
     * @date 2020/5/23 10:09
     */
    @Override
    public Long zSetRemByRank(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            log.error("移除zset缓存区间元素异常 =" + key, e);
            return null;
        }
    }

    @Override
    public boolean sAdd(String key, Object value) {
        try {
            Long result = redisTemplate.opsForSet().add(key, value);
            if (result != null && result == 1) {
                return true;
            }
        } catch (Exception e) {
            log.error(String.format("set添加成员异常 key=[%s] value=[%s]", key, value), e);
        }
        return false;
    }

    @Override
    public boolean sIsMember(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error(String.format("set判断是否成员异常 key=[%s] value=[%s]", key, value), e);
        }
        return false;
    }

//    @Override
//    public void setBit(String key, long offset,boolean value) {
//        try {
//            redisTemplate.opsForValue().setBit(key,offset,value);
//        } catch (Exception e) {
//            log.error("set redis bit异常 =" + key, e);
//        }
//
//    }

//    @Override
//    public boolean getBit(String key, long offset) {
//        try {
//            return  redisTemplate.opsForValue().getBit(key,offset);
//        } catch (Exception e) {
//            log.error("get redis bit异常 =" + key, e);
//            return false;
//        }
//
//    }
}
