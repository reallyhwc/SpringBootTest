package com.xuhu.springboodemo.core.redis;

import com.xuhu.springboodemo.core.service.CacheService;
import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuhu
 * @date 2021-03-28 22:00
 * 测试遵循：https://zhuanlan.zhihu.com/p/139528556
 */
@SpringBootTest
@Slf4j
public class RedisTest{

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private CacheService redisService;
    @Autowired
    private TestCityService testCityService;

    // string 入库
    @Test
    public void testForValue1(){
        String key = "zszxz";
        String value = "知识追寻者1111";
        redisTemplate.opsForValue().set(key, value);
    }

    // string 出库
    @Test
    public void testForValue2(){
        String key = "zszxz";
        Object value = redisTemplate.opsForValue().get(key);
        // 知识追寻者
        System.out.println(value);
    }
    // string key过期时间入库
    @Test
    public void testForValue3(){
        String key = "today";
        String value = "周六";
        long time = 60;
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Test
    void initTestCityToCache(){
        List<TestCity> testCityList = testCityService.getCityList();
        // 批量添加zSet缓存
        Set<ZSetOperations.TypedTuple<Object>> testCityZSet = new HashSet<>();
        testCityList.forEach(testCity -> {

            testCityZSet.add(new DefaultTypedTuple<>(testCity, testCity.getId().doubleValue()));
        });

        String key = "xuhu:test:city";

        redisService.batchZSet(key, testCityZSet);
    }

}
