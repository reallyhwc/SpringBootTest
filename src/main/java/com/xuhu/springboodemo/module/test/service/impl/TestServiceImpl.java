package com.xuhu.springboodemo.module.test.service.impl;

import com.xuhu.springboodemo.core.JDKUtil.LocalStringUtils;
import com.xuhu.springboodemo.module.test.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author xuhu
 * @date 2021-03-28 15:09
 */
@Service
public class TestServiceImpl implements TestService {


    /**
     * 根据输入长度，随机生成字符串
     * @param len len 长度
     * @return 随机字符串
     */
    @Override
    public String getTestString(Integer len) {
        return LocalStringUtils.genRandomNum(len);
    }
}
