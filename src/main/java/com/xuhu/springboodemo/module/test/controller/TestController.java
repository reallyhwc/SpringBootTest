package com.xuhu.springboodemo.module.test.controller;

import com.xuhu.springboodemo.core.domain.ResultMsg;
import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.query.TestCityQuery;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import com.xuhu.springboodemo.module.test.service.TestService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private TestCityService testCityService;

    @GetMapping(value = "/getStrTest")
    public String returnNumTest(int len){
        return testService.getTestString(len);
    }

    /**
     * getCityList
     * @return msg
     */
    @RequestMapping(value = "/getCityList",method = RequestMethod.GET)
    public ResultMsg getCityList(){
        log.error("aaaaa");
        return new ResultMsg<>("成功",testCityService.getCityList());
    }

    /**
     * getCityPageList
     * @return msg
     */
    @RequestMapping(value = "/getCityPageList",method = RequestMethod.GET)
    public ResultMsg getCityPageList(){
        log.error("getCityPageList");
        return new ResultMsg<>("成功",testCityService.getCityPageList(new TestCityQuery()));
    }

}
