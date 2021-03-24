package com.xuhu.springboottsetdemo.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhu
 * @date 2021-03-24 20:57
 */
@RestController
@RequestMapping(value = "/api/core/test")
public class CoreTestController {

    @RequestMapping(value = "/test")
    public Integer test(){
        return 111;
    }
}
