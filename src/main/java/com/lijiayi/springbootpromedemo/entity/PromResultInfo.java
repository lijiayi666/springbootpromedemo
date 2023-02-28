package com.lijiayi.springbootpromedemo.entity;

import lombok.Data;

@Data
public class PromResultInfo {


    /**
     * prometheus指标属性
     */
    private PromMetricInfo metric;

    /**
     * prometheus指标值
     */
    private String[] value;

}
