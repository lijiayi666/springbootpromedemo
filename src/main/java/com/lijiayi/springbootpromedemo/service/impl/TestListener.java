package com.lijiayi.springbootpromedemo.service.impl;

import com.lijiayi.springbootpromedemo.event.MyListenerEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener implements ApplicationListener<MyListenerEvent> {

    @Override
    public void onApplicationEvent(MyListenerEvent event) {
        String name = event.getName();
        System.out.println("监听到用户名是:" + name);
    }
}
