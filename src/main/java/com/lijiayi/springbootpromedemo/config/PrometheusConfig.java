package com.lijiayi.springbootpromedemo.config;

import io.prometheus.client.exporter.MetricsServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfig {

    /**
     * 采集端的访问路径为项目ip:port/metrics
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new MetricsServlet(), "/metrics");
    }
}
