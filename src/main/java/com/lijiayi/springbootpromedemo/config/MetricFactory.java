package com.lijiayi.springbootpromedemo.config;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// 初始化时注册不同类型跟名称的指标
@Component
public class MetricFactory {

    @Autowired
    ApplicationContext applicationContext;

    @Bean("counterFactory")
    public Counter counterFactory() {
        String[] inactiveFrameworkLabels = {"code","serverName","api","hostIp"};
        System.out.println(applicationContext.getId());
        System.out.println(applicationContext.getApplicationName());
        System.out.println(applicationContext.getDisplayName());
        return Counter.build()
                .name("lijiayi_http_request_counter")
                .labelNames(inactiveFrameworkLabels)
                .help("在接口层面记录交易次数，用于计算交易成功率")
                .register();
    }

    @Bean("counterFactory1")
    public Counter counterFactory1() {
        String[] inactiveFrameworkLabels = {"code","serverName","api","hostIp"};
        return Counter.build()
                .name("lijiayi_http_request_counter_testSave")
                .labelNames(inactiveFrameworkLabels)
                .help("在接口层面记录交易次数，用于计算交易成功率 ")
                .register();
    }

    @Bean("summaryFactory")
    public Summary summaryFactory() {
        String[] inactiveFrameworkLabels = {"code","serverName","api","hostIp"};
        return Summary.build()
                .name("lijiayi_http_request_summary")
                .labelNames(inactiveFrameworkLabels)
                .help("this is summary")
                .register();
    }

    @Bean("gaugeFactory")
    public Gauge gaugeFactory() {
        String[] inactiveFrameworkLabels = {"serverName","hostIp"};
        return Gauge.build()
                .name("lijiayi_get_last_time")
                .labelNames(inactiveFrameworkLabels)
                .help("记录上一次调用时间戳")
                .register();
    }

//    @Bean("myCounterFactory")
    public MyCollector myCounterFactory() {
        return new MyCollector().register();
    }

    @Bean("counterAllInterfaceFactory")
    public Counter counterAllInterfaceFactory() {
        String[] inactiveFrameworkLabels = {"serverName","centerName","hostIp"};
        return Counter.build()
                .name("lijiayi_all_http_request_counter")
                .labelNames(inactiveFrameworkLabels)
                .help("记录交易次数，用于计算交易环比骤变")
                .register();
    }

}