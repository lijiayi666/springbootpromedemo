package com.lijiayi.springbootpromedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.lijiayi.springbootpromedemo.metrics.PushHistogram;
import com.lijiayi.springbootpromedemo.metrics.PushSummary;
import com.lijiayi.springbootpromedemo.service.impl.TestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController
@RequestMapping("lijiayi")
public class TestController {

    @Autowired
    private TestServiceImpl testService;

    @Autowired
    PushHistogram pushHistogram;

    @Autowired
    PushSummary pushSummary;

//    @Autowired
//    PushGauge pushGauge;

    // 测试采集
    @ResponseBody
    @GetMapping("/test1/{isOk}&{center}")
//    @MethodMetrics
    public String test(@PathVariable("isOk") Boolean isOk, @PathVariable("center") String center) {
        log.info("---------------   进入test  -------------");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("center", center);
        if (!isOk) {
            jsonObject.put("code", "999999");
            jsonObject.put("result", "get result error!");
        } else {
            jsonObject.put("code", "20000");
            jsonObject.put("result", "hello word!");
        }
//        pushHistogram.histogram(center);
//        pushSummary.summary();
        pushHistogram.histogram();
//        pushGauge.gauge(center);
        return JSONObject.toJSONString(jsonObject);
    }

    // 测试调用prometheus 的http api
    @ResponseBody
    @GetMapping("/testSave/{rt}&{count}")
    public String testSave(@PathVariable("rt") Integer rt, @PathVariable("count") Integer count) {
        System.out.println("调用testSave成功");
//        pushHistogram.histogram(rt);
        Random random = new Random(100);

        long sum = 0;
        long start3 = System.currentTimeMillis();
        for (; sum < 100000; ) {
            int rtR = Math.abs(random.nextInt() % 500);
            int cntR = Math.abs(random.nextInt() % 500);
//            pushHistogram.batchAdd(cntR, rtR);
            pushSummary.batchAdd(cntR, rtR);
            sum += cntR;

        }
        System.out.println("第三个循环耗时： "  + (System.currentTimeMillis() - start3));
//        pushHistogram.batchAddTwo(count, rt);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/webhook")
    public String webhook(@RequestBody String str) {
        log.info("*************  " + str);
        log.info("--------  alertmanager触发了webhook接口  -------------");
        return "alertmanager触发了webhook接口";
    }

}
