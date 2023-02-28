package com.lijiayi.springbootpromedemo.schedule;

import com.lijiayi.springbootpromedemo.metrics.PushRequestCounter;
import com.lijiayi.springbootpromedemo.metrics.PushResponseAvgTimer;
import com.lijiayi.springbootpromedemo.metrics.PushResponseMaxTimer;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class SendToPushGatewayTask {

    @Autowired
    PushRequestCounter requestCounter;

    @Autowired
    PushResponseAvgTimer responseAvgTimer;

    @Autowired
    PushResponseMaxTimer responseMaxTimer;

    public static final CollectorRegistry COLLECTOR_REGISTRY = new CollectorRegistry();

    private static PushGateway pushGateway = new PushGateway("101.43.140.106:9091");

    @Scheduled(cron = "*/5 * * * * *")
    public void send() {
        log.info("定时任务 5s一次:{}", LocalDateTime.now().toString());
        try {
            // 推送到pushGateway
            pushGateway.pushAdd(COLLECTOR_REGISTRY, "pushgateway");

            // 重置最大时间
            responseMaxTimer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
