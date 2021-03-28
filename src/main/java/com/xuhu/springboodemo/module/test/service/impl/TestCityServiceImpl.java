package com.xuhu.springboodemo.module.test.service.impl;

import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.mapper.TestCityMapper;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuhu
 * @date 2021-03-28 15:55
 */
@Service
public class TestCityServiceImpl implements TestCityService {

    @Autowired
    private TestCityMapper testCityMapper;

    /**
     * getList
     * @return list
     */
    @Override
    public List<TestCity> getCityList() {
        return testCityMapper.getCityList();
    }
}
