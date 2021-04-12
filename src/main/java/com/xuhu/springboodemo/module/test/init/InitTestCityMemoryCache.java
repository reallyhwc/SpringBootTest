package com.xuhu.springboodemo.module.test.init;

import com.xuhu.springboodemo.module.test.service.TestCityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuhu
 * @date 2020-07-22 16:33
 * 初始化关键词设置到缓存中
 *
 */
@Component
@Slf4j
public class InitTestCityMemoryCache implements InitializingBean {

    @Autowired
    private TestCityService testCityService;

    @Override
    public void afterPropertiesSet(){
        log.error("开始执行项目启动-内存cityCache设置！");
//        testCityService.initTestCityMemoryCache();
        log.error("项目启动-初始化-内存cityCache设置完成！");
    }
}
