package com.xuhu.springboodemo.module.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuhu.springboodemo.core.service.CacheService;
import com.xuhu.springboodemo.core.vo.BaseVo;
import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.mapper.TestCityMapper;
import com.xuhu.springboodemo.module.test.query.TestCityQuery;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author xuhu
 * @date 2021-03-28 15:55
 */
@Service
public class TestCityServiceImpl implements TestCityService {

    @Autowired
    private TestCityMapper testCityMapper;
    @Autowired
    private CacheService redisService;

    private List<TestCity> testCityMemoryList;

    /**
     * getList
     * @return list
     */
    @Override
    public List<TestCity> getCityList() {
        return testCityMapper.getCityList();
    }

    /**
     * 获取city分页List
     * @param query query 查询条件
     * @return 分页List
     */
    @Override
    public BaseVo<TestCity> getCityPageList(TestCityQuery query) {
        query.setPage((int) (Math.random() * 408));
        query.validatePage();
        //根据创建时间倒排List
        PageHelper.startPage(query.getPage(),query.getRows());
        List<TestCity> list = testCityMapper.getCityList();
        PageInfo<TestCity> pageInfo = new PageInfo<>(list);
        return new BaseVo<>(list, (int)pageInfo.getTotal(), query.getPage(), query.getRows());
    }

    /**
     * 获取city分页List fromRedisCache
     * @param query query 查询条件
     * @return 分页List
     */
    @Override
    public BaseVo<TestCity> getCityPageListFormRedisCache(TestCityQuery query) {
        query.setPage((int) (Math.random() * 408));
        query.validatePage();
        int total = Math.toIntExact(redisService.getZSetCount("xuhu:test:city"));
        Set<Object> set = redisService.getZSet("xuhu:test:city", query.getOffset(), query.getRows());

        List<TestCity> list = (List<TestCity>)(List) Arrays.asList(set);
        return new BaseVo<>(list, total, query.getPage(), query.getRows());
    }

    /**
     * 获取city分页List fromMemoryCache
     * @param query query 查询条件
     * @return 分页List
     */
    @Override
    public BaseVo<TestCity> getCityPageListFormMemoryCache(TestCityQuery query) {
        query.setPage((int) (Math.random() * 408));
        query.validatePage();

        int total = testCityMemoryList.size();

        if (query.getOffset() >= total){
            return new BaseVo<>();
        }

        List<TestCity> list = testCityMemoryList.subList(query.getOffset(), Math.min((query.getOffset() + query.getRows()), total));

        return new BaseVo<>(list, total, query.getPage(), query.getRows());
    }

    /**
     * initTestCityMemoryCache
     */
    @Override
    public void initTestCityMemoryCache() {
        testCityMemoryList = testCityMapper.getCityList();
    }
}
