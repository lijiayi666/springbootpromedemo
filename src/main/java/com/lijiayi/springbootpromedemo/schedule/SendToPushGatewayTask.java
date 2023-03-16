package com.lijiayi.springbootpromedemo.schedule;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.PushGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SendToPushGatewayTask {

    public static final CollectorRegistry COLLECTOR_REGISTRY = new CollectorRegistry();

    private static PushGateway pushGateway = new PushGateway("192.168.129.201:9091");

    @Scheduled(cron = "*/10 * * * * *")
    public void send() {
        log.info("定时任务 5s一次:{}", LocalDateTime.now().toString());
        try {
            Map<String, String> map = new HashMap<>();
            //
            map.put("instance", "192.168.129.666");
            // 推送到pushGateway
            pushGateway.pushAdd(COLLECTOR_REGISTRY, "pushgateway", map);
        } catch (IOException e) {
            log.error("推送异常", LocalDateTime.now().toString());
            e.printStackTrace();
        }
    }
}
