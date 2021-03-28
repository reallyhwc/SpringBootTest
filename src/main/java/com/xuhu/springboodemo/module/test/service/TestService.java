package com.xuhu.springboodemo.module.test.service;

/**
 * @author xuhu
 * @date 2021-03-28 15:09
 */
public interface TestService {

    /**
     * 根据输入长度，随机生成字符串
     * @param len len 长度
     * @return 随机字符串
     */
    String getTestString(Integer len);
}
