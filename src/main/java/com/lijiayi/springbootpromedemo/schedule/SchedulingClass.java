package com.lijiayi.springbootpromedemo.schedule;

import com.lijiayi.springbootpromedemo.entity.PromDataInfo;
import com.lijiayi.springbootpromedemo.entity.PromMetricInfo;
import com.lijiayi.springbootpromedemo.entity.PromResponceInfo;
import com.lijiayi.springbootpromedemo.entity.PromResultInfo;
import com.lijiayi.springbootpromedemo.util.PromInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class SchedulingClass {

    // 报警阈值一分钟
    static final long THRESHOLD_TIME = 1000 * 60;

    // 中心
    static final String[] CENTER_LIST = {"西安中心", "成都中心"};

    // 交易环比骤变报警阈值
    static final Integer ALERT_PERCENT = 1;

    // 2分钟
    @Scheduled(fixedRate=1000 * 60 * 2)
    private void lijiayiGetLastTimeScheduled() {
        long nowTime = System.currentTimeMillis();
        log.info("调用定时任务 lijiayiGetLastTimeScheduled");
        PromResponceInfo promeData = PromInfoUtils.lijiayiGetLastTime();
        PromDataInfo data = promeData.getData();
        List<PromResultInfo> result = data.getResult();
        if (CollectionUtils.isEmpty(result)) {
            log.error("service is error!");
        } else {
            for (PromResultInfo promResultInfo : result) {
                String[] value = promResultInfo.getValue();
                String timeTemp = value[1];
                Long lastTime = Long.valueOf(timeTemp);
                long leadTime = nowTime - lastTime;
                if (leadTime > THRESHOLD_TIME) {
                    // 调用webhook
                    log.error("------------- 长时间无交易 --------------");
                    log.info("--------  调用webhook接口  -------------");
                } else {
                    log.info("距离上次交易时间: {}ms", THRESHOLD_TIME - leadTime);
                }
            }
        }
    }

    // 2分钟
    @Scheduled(fixedRate=1000 * 60 * 2)
    private void lijiayiAllHttpRequestCounterTotalScheduled() {
        log.info("调用定时任务-----lijiayiAllHttpRequestCounterTotalScheduled");
        PromResponceInfo promeDataNow = PromInfoUtils.lijiayiAllHttpRequestCounterTotalNow();
        PromDataInfo dataNow = promeDataNow.getData();
        List<PromResultInfo> resultNow = dataNow.getResult();
        PromResponceInfo promeDataBefore = PromInfoUtils.lijiayiAllHttpRequestCounterTotalBefore();
        PromDataInfo data = promeDataBefore.getData();
        List<PromResultInfo> resultBefore = data.getResult();
        if (CollectionUtils.isEmpty(resultBefore)) {
            log.error("昨日同时段无交易");
            return;
        } else if (CollectionUtils.isEmpty(resultNow)){
            log.error("今日该时段无交易");
            return;
        } else {
            // 解析将查询内容根据不同的中心存入map（如指标涉及服务名也一样）
            HashMap<String, Integer> resultMapBefore = getResultMap(resultBefore);
            HashMap<String, Integer> resultMapNow = getResultMap(resultNow);
            for (String center : CENTER_LIST) {
                // 计算交易环比骤变  （此处简单的除一下求个百分比  小于一 认为达到告警阈值）
                Integer nowCount = resultMapNow.get(center);
                Integer beforeCount = resultMapBefore.get(center);
                if (nowCount == null) {
                    log.info("该时段无交易！");
                    continue;
                } else if (beforeCount == null) {
                    log.info("历史该时段无交易！");
                    continue;
                }
                if (nowCount == 0 && beforeCount == 0) {
                    log.info("该时段与历史时段均无交易！");
                    continue;
                } else if (nowCount == 0 && beforeCount > 0) {
                    log.error("------------- 交易环比骤变异常 --------------");
                    continue;
                } else if (nowCount > 0 && beforeCount == 0) {
                    continue;
                } else {
                    int percent = nowCount / beforeCount;

                    if (percent < ALERT_PERCENT) {
                        // 调用webhook
                        log.error("------------- 交易环比骤变异常 --------------");
                        log.info("--------  调用webhook接口  -------------");
                    }
                }
            }
        }
    }

    private HashMap<String, Integer> getResultMap(List<PromResultInfo> result) {
        HashMap<String, Integer> map = new HashMap<>();
        for (PromResultInfo promResultInfo : result) {
            String[] value = promResultInfo.getValue();
            PromMetricInfo metric = promResultInfo.getMetric();
            String centerName = metric.getCenterName();
            String callTimes = value[1];
            if (StringUtils.isEmpty(map.get(centerName))) {
                map.put(centerName, Integer.parseInt(callTimes));
            } else {
                map.put(centerName, Integer.parseInt(callTimes) + map.get(centerName));
            }
            log.info("这一阶段调用了{}次", callTimes);
        }
        return map;
    }
}
