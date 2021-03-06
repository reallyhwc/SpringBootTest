package com.xuhu.springboodemo.module.test.mapper;

import com.xuhu.springboodemo.module.test.domain.TestCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xuhu
 * @date 2021-03-28 15:55
 */
@Mapper
public interface TestCityMapper {

    /**
     * 获取cityList
     * @return list
     */
    @Select(value = "select * from city")
    List<TestCity> getCityList();
}
