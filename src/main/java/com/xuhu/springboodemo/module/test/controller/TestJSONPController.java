package com.xuhu.springboodemo.module.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuhu
 * @date 2021-03-29 16:37
 */
@Controller
public class TestJSONPController {

    @RequestMapping(value = "/jsonp", method = RequestMethod.GET)
    public String jsonp(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        return callback + "({result:'jsonp'})";
    }
}
