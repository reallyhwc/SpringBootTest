package com.xuhu.springboodemo.module.test.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuhu
 * @date 2021-03-28 15:56
 */
@Data
public class TestCity implements Serializable {

    /** ID */
    private Integer id;
    /** name */
    private String name;
    /** 国家Code */
    private String countryCode;
    /** 描述 */
    private String district;
    /** 人数 */
    private Integer population;
}
