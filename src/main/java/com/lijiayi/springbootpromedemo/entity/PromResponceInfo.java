package com.lijiayi.springbootpromedemo.entity;

import lombok.Data;

@Data
public class PromResponceInfo {

    /**
     * 状态
     * 成功-- success
     */
    private String status;

    /**
     * prometheus指标属性和值
     */
    private PromDataInfo data;


}