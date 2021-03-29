package com.xuhu.springboodemo.module.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuhu.springboodemo.core.domain.ResultMsg;
import com.xuhu.springboodemo.module.test.domain.TestCity;
import com.xuhu.springboodemo.module.test.service.TestCityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuhu
 * @date 2021-03-29 16:37
 */
@RestController
@RequestMapping(value = "/api/test")
@Slf4j
public class TestJSONPController {

    @Autowired
    private TestCityService testCityService;

    @RequestMapping(value = "/jsonp", method = RequestMethod.GET)
    public String jsonp(HttpServletRequest request) {
        String callback = request.getParameter("callback");

        ResultMsg msg = ResultMsg.ok("成功", testCityService.getCityList().get(0));

        String oldAns = callback+"({result:'jsonp'})";
        String newAns = callback + String.format("(%s)", JSONObject.toJSONString(msg));

        log.error(oldAns);
        log.error(newAns);

        return newAns;
    }
}
