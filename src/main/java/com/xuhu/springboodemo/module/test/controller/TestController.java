package com.xuhu.springboodemo.module.test.controller;

import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import com.xuhu.springboodemo.module.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xuhu
 * @date 2021-03-28 15:08
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private TestCityService testCityService;

    @GetMapping(value = "/getStrTest")
    public String returnNumTest(int len){
        return testService.getTestString(len);
    }

    @RequestMapping(value = "/getCityList",method = RequestMethod.GET, produces = "application/json")
    public TestCity getCityList(){
        List<TestCity> list = testCityService.getCityList();
        return list.get(0);
    }
}
