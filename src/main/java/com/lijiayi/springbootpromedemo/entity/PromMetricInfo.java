package com.lijiayi.springbootpromedemo.entity;

import lombok.Data;

@Data
public class PromMetricInfo {

    /**
     * prometheus指标名称
     */
    private String __name__;

    /**
     * prometheus实例名称
     */
    private String instance;

    /**
     * prometheus任务名称
     */
    private String job;

    // 中心名
    private String centerName;

    // 服务ip地址
    private String hostIp;

    // 服务名称
    private String serverName;

}
