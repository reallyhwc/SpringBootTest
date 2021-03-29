package com.xuhu.springboodemo.module.test.service;

import com.xuhu.springboodemo.core.vo.BaseVo;
import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.query.TestCityQuery;

import java.util.List;

/**
 * @author xuhu
 * @date 2021-03-28 15:54
 */
public interface TestCityService {

    /**
     * getList
     * @return list
     */
    List<TestCity> getCityList();

    /**
     * 获取city分页List
     * @param query query 查询条件
     * @return 分页List
     */
    BaseVo<TestCity> getCityPageList(TestCityQuery query);

    /**
     * 获取city分页List fromRedisCache
     * @param query query 查询条件
     * @return 分页List
     */
    BaseVo<TestCity> getCityPageListFormRedisCache(TestCityQuery query);

    /**
     * 获取city分页List fromMemoryCache
     * @param query query 查询条件
     * @return 分页List
     */
    BaseVo<TestCity> getCityPageListFormMemoryCache(TestCityQuery query);

    /**
     * initTestCityMemoryCache
     */
    void initTestCityMemoryCache();
}
