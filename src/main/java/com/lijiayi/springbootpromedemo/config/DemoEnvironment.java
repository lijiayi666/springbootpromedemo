package com.lijiayi.springbootpromedemo.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class DemoEnvironment implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String name = "applicationConfig: [classpath:/application.properties]";
        MapPropertySource propertySource = (MapPropertySource) environment.getPropertySources().get(name);
        Map<String, Object> source = propertySource.getSource();

        source.put("arthas.test", "lijiayiNewAgentId");
//        environment.getPropertySources().replace(name, new MapPropertySource(name, map));
    }
}
