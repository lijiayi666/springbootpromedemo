//package com.lijiayi.springbootpromedemo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class PushServletFilterConfig {
//
//    @Value("${prometheus.demo.pathAndExporter}")
//    String interceptUrl;
//
//    @Bean
//    public FilterRegistrationBean getFilterRegistrationBean() {
//        FilterRegistrationBean bean = new FilterRegistrationBean(new PushPrometheusFilter());
//        String[] urls = interceptUrl.split(";");
//        for (String url : urls) {
//            bean.addUrlPatterns(url);
//        }
//        return bean;
//    }
//
//}
