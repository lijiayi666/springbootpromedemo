package com.lijiayi.springbootpromedemo.controller;

import com.alibaba.arthas.spring.ArthasProperties;
import com.alibaba.fastjson.JSONObject;
import com.lijiayi.springbootpromedemo.entity.ScheduleEctity;
import com.lijiayi.springbootpromedemo.event.MyListenerEvent;
import com.lijiayi.springbootpromedemo.metrics.PushGauge;
import com.lijiayi.springbootpromedemo.metrics.PushHistogram;
import com.lijiayi.springbootpromedemo.metrics.PushRequestCounter;
import com.lijiayi.springbootpromedemo.metrics.PushSummary;
import com.lijiayi.springbootpromedemo.service.TestScheduleService;
import com.lijiayi.springbootpromedemo.service.impl.TestServiceImpl;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("lijiayi")
public class TestController {

    public static final CollectorRegistry COLLECTOR_REGISTRY = new CollectorRegistry();

    private static PushGateway pushGateway = new PushGateway("192.168.129.201:9091");

    @Autowired
    private TestServiceImpl testService;

    @Autowired
    PushHistogram pushHistogram;

    @Autowired
    PushSummary pushSummary;

    @Autowired
    PushRequestCounter counter;

    @Autowired
    PushGauge pushGauge;

    @Autowired
    private ApplicationContext springApplication;

    @Autowired
    private TestScheduleService testScheduleService;

    // 测试采集
    @ResponseBody
    @GetMapping("/test1/{inc}")
    public String test(@PathVariable("inc") Double inc) {
        JSONObject jsonObject = new JSONObject();
        pushGauge.gauge(inc);
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
        System.out.println("第三个循环耗时： " + (System.currentTimeMillis() - start3));
//        pushHistogram.batchAddTwo(count, rt);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/webhook")
    public String webhook(@RequestBody String str) {
        ArthasProperties bean = applicationContext.getBean(ArthasProperties.class);
        System.out.println("--------  alertmanager触发了webhook接口  new-------------");
        return "alertmanager触发了webhook接口 new";
    }

    public Void a (){
        return null;
    }

    // 测试采集
    @ResponseBody
    @GetMapping("/testCounter")
//    @MethodMetrics
    public String testCounter() {
        return "8082 ok";
    }

    @GetMapping("send")
    public void send() {
        try {
            Map<String, String> map = new HashMap<>();
            //
            map.put("instance", "192.168.129.666");
            // 推送到pushGateway
            pushGateway.pushAdd(COLLECTOR_REGISTRY, "pushgateway", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/testListener")
    public void testListener(@RequestParam String name) {
        System.out.println("塞入的name是:" + name);
        MyListenerEvent myListenerEvent = new MyListenerEvent(this, name);
        applicationContext.publishEvent(myListenerEvent);
    }

    @PostMapping("/addSchedule")
    public void addSchedule(@RequestBody ScheduleEctity scheduleEctity) {
        testScheduleService.addTask(scheduleEctity);
    }

    @PostMapping("/updateSchedule")
    public void updateSchedule(@RequestBody ScheduleEctity scheduleEctity) {
        testScheduleService.updateTask(scheduleEctity);
    }

}
