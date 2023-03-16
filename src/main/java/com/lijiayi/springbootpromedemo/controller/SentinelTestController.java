package com.lijiayi.springbootpromedemo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class SentinelTestController {

    @GetMapping("hello")
    @SentinelResource(value = "test.hello", fallback = "helloBlock")
    public String hello(String name){
        return "hello,"+name;
    }

    public String helloBlock(String name, Throwable e){
        return "Block,"+name;
    }

    @GetMapping("hello1")
    @SentinelResource(value = "test.hello1", fallback = "helloException")
    public String hello1(String name){
        Random random = new Random();
        int i = random.nextInt(10);
        if (i == 1) {
            throw new RuntimeException("异常啦");
        }
        return "hello1,"+name;
    }

    public String helloException(String name, Throwable e){
        return "Exception,"+name;
    }
}
