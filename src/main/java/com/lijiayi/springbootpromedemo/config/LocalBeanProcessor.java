package com.lijiayi.springbootpromedemo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.HashMap;

//@Component
public class LocalBeanProcessor implements BeanPostProcessor {

    @Resource
    private Environment environment;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equalsIgnoreCase("arthasConfigMap")) {
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                String port = environment.getProperty("server.port");
                String agentId = ip + ":" + port;
                System.out.println(agentId);
                HashMap map = (HashMap) bean;
                map.put("agent-id", agentId);
                map.put("disabled-commands", "dashboard,stop");
                bean = map;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
