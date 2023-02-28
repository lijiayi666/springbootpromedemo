package com.lijiayi.springbootpromedemo.aop.impl;

import com.alibaba.fastjson.JSONObject;
import com.lijiayi.springbootpromedemo.entity.TestAopEntity;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Slf4j
@Component
public class PrometheusAnnotationAspect {

    @Qualifier("counterFactory")
    @Autowired
    private Counter counterFactory;

    @Qualifier("counterAllInterfaceFactory")
    @Autowired
    private Counter counterAllInterfaceFactory;

    @Autowired
    private Summary summary;

    @Autowired
    private Gauge gauge;

//    @Pointcut("@annotation(com.lijiayi.springbootpromedemo.aop.MethodMetrics)")
    @Pointcut("execution(* com.lijiayi.springbootpromedemo.service.TestService.testAopParamsGet(..))")
    public void promePointCut() {
    }

    @Around(value = "promePointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //拼接请求内容
        System.out.println("\n请求路径:" + request.getRequestURI());
        long startTime = System.currentTimeMillis();// 开始时间
        System.out.println("进入pointcut");
        // 获取接口参数
        TestAopEntity testAopEntity = (TestAopEntity) joinPoint.getArgs()[0];
        String name = testAopEntity.getName();
        return processMetric(joinPoint, startTime);
    }

    private Object processMetric(ProceedingJoinPoint joinPoint, long startTime) {
        Object result = null;
        long handleTime;
        try {
            log.info("进入try");
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            handleTime = System.currentTimeMillis() - startTime;
            log.info("请求类名>>>:{},请求方法名>>>:{},请求耗时>>>:{}ms", joinPoint.getTarget().getClass().getName(), ((MethodSignature) joinPoint.getSignature()).getName(), handleTime);
        }
        String code = "";
        if (!StringUtils.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            code = jsonObject.getString("code");
        }
        // 获取counterFactory收集器的标签value
        String[] counterLabels = getCounterLabels(joinPoint, code);
        // 获取gauge收集器的标签value
        String[] gaugeLabels = getGaugeLabels();
        // 获取counterAllInterfaceFactory收集器的标签value
        String[] counterAllInterfaceLabels = getCounterAllLabels();
        Gauge.Child child = new Gauge.Child();
//        child.set();
        counterFactory.labels(counterLabels).inc();
        gauge.labels(gaugeLabels).set(1213);
        counterAllInterfaceFactory.labels(counterAllInterfaceLabels).inc();
        summary.labels(counterLabels);
        return result;
    }

    private String[] getCounterLabels(ProceedingJoinPoint joinPoint, String code) {
        // 创建一个list用于存储value
        List<String> counterValueList = new ArrayList<>();
        // 加入code
        counterValueList.add(code);
        // 加入serverName
        counterValueList.add("userMgmtService");
        // 加入接口
        counterValueList.add(joinPoint.getSignature().getName());
        // 加入ip
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get ipAddr error!");
        }
        counterValueList.add(hostAddress);
        // 将接口返回封装到counter
        String[] counterLabels = counterValueList.toArray(new String[counterValueList.size()]);
        return counterLabels;
    }

    private String[] getCounterAllLabels() {
        Random random = new Random();
        int ran = random.nextInt(2 -1 +1);
        int ran2 = random.nextInt(2 -1 +1);
        // 创建一个list用于存储value
        List<String> allCounterValueList = new ArrayList<>();
        if (ran == 0) {
            // 加入serverName
            allCounterValueList.add("userMgmtService");
        } else {
            allCounterValueList.add("roleMgmtService");
        }
        if (ran2 == 0) {
            // 加入centerName
            allCounterValueList.add("西安中心");
        } else {
            allCounterValueList.add("成都中心");
        }
        // 加入ip
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get ipAddr error!");
        }
        allCounterValueList.add(hostAddress);
        // 将接口返回封装到counter
        String[] gaugeLabels = allCounterValueList.toArray(new String[allCounterValueList.size()]);
        return gaugeLabels;
    }

    private String[] getGaugeLabels() {
        // 创建一个list用于存储value
        List<String> gaugeValueList = new ArrayList<>();
        // 加入serverName
        gaugeValueList.add("userMgmtService");
        // 加入ip
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get ipAddr error!");
        }
        gaugeValueList.add(hostAddress);
        // 将接口返回封装到counter
        String[] gaugeLabels = gaugeValueList.toArray(new String[gaugeValueList.size()]);
        return gaugeLabels;
    }


    public static ConcurrentHashMap<Long, ConcurrentHashMap<String, Integer>> resultMap = new ConcurrentHashMap();
}
