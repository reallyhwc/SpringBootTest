package com.xuhu.springboodemo.core.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CacheService extends Serializable {

    /**
     * 根据模式获取键
     *
     * @param pattern
     * @return
     */
    Set<String> getKeys(String... pattern);

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    boolean expire(String key, long time);

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    void del(String... key);

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    boolean set(String key, Object value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    boolean set(String key, Object value, long time);

    /**
     * 批量往redis中设置多个键值对
     *
     * @param map map
     */
    void mSet(Map<String, Object> map);

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    long incr(String key, long delta);

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    long incr(String key);

    /**
     * 递减
     *
     * @param key 键
     * @return
     */
    long decr(String key, long delta);

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object hget(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hmget(String key);

    /**
     * 获取hashKeys集合对应的所有值
     *
     * @param key
     * @param hashKeys
     * @return 对应的多个值
     */
    List<Object> hmget(String key, Collection<String> hashKeys);

    /**
     * 获取hashKeys集合对应的所有键值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    Map<Object, Object> hmgetForKV(String key, Collection<String> hashKeys);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    boolean hmset(String key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    boolean hmset(String key, Map<String, Object> map, long time);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    boolean hset(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    boolean hset(String key, String item, Object value, long time);

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void hdel(String key, String... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    boolean hHasKey(String key, String item);

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    double hincr(String key, String item, double by);

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    double hdecr(String key, String item, double by);

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    Set<Object> sGet(String key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    boolean sHasKey(String key, Object value);

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    long sSet(String key, Object... values);


    boolean zSet(String key, String value, double score);

    Set<Object> getZSet(String key, long offset, long count);

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
    Set<Object> getZSetByScore(String key, double scoreBegin, double scoreEnd, long offset, long count);

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
    Set<Object> reverseGetAllZSet(String key, long start, long end);

    /**
     * 正序获取指定key的zet数据
     *
     * @param key    key
     * @param offset offset
     * @param count  count  -1表示获取全部
     * @return Set<Object>
     */
    Set<Object> getPositiveZSet(String key, long offset, long count);

    Set<ZSetOperations.TypedTuple<Object>> getZSetWithScore(String key, long offset, long count);

    Long getZSetCount(String key);

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    long sSetAndTime(String key, long time, Object... values);

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    long sGetSetSize(String key);

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    long setRemove(String key, Object... values);

    long zsetRemove(String key, Object... values);

    //===============================list=================================
    List<Object> getAllList(String key);

    //===============================list=================================
    List<Object> lGet(String key);

    List<Object> lGet(String key, long start);

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    List<Object> lGet(String key, long start, long end);

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    long lGetListSize(String key);

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    Object lGetIndex(String key, long index);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(String key, Object value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(String key, Object value, long time);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    boolean lSet(List<Object> value, String key);

    /**
     * 将obj 放入redisList中 （右边插入）
     *
     * @param key   key
     * @param value 待插入value
     * @return 是否成功
     */
    boolean lRightSet(String key, Object value);

    boolean lRightSetAll(String key, Collection<?> values);

    boolean leftPushAll(List<Object> value, String key);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    boolean lSet(List<Object> value, String key, long time);


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    boolean lUpdateIndex(String key, long index, Object value);

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    long lRemove(String key, long count, Object value);


    boolean setnx(String lockKey, String expiresStr);


    String getSet(String lockKey, String expiresStr);

    boolean lSet(String key, String value);

    Object rGet(String key);

    Boolean exists(String key);

    void lTrim(String key, Long start, Long stop);

    Long batchZSet(String key, Set<ZSetOperations.TypedTuple<Object>> set);

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
    Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end);


    /**
     * 功能描述: 查询zSet某个member的分数
     *
     * @param key
     * @return
     * @author zyd
     * @date 2020/5/23 10:09
     */
    Double zSetGetScore(String key, String member);


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
    Long zSetRemByRank(String key, long start, long end);

    /** set bitmap */
//  void setBit(String key,long offset,boolean value);

    /**
     * get bitmap
     */
//  boolean getBit(String key, long offset);

    boolean sAdd(String key, Object value);

    boolean sIsMember(String key, Object value);

}

