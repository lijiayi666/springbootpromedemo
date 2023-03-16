package com.lijiayi.springbootpromedemo.metrics;

import com.lijiayi.springbootpromedemo.schedule.SendToPushGatewayTask;
import io.prometheus.client.Summary;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.DoubleStream;

@Component
public class PushSummary {
    public static Summary summary = Summary.build()
            .name("summary_template")
            .help("histogram of bulk sizes to correlate with duration")
            .labelNames("url", "ip", "serverName")
            .quantile(0.99, 0.01)
            .quantile(0.95, 0.01)
            .quantile(0.50, 0.01)
            .register(SendToPushGatewayTask.COLLECTOR_REGISTRY);

    public void summary() {
        Random random = new Random();
        int i = random.nextInt(100);
        if (i > 30 && i <= 70) {
            summary.labels("/test001", "192.168.129.666", "testServer001").observe(i);
        } else if (i > 70 && i <= 90){
            summary.labels("/test002", "192.168.129.666", "testServer001").observe(i);
        } else if (i > 90) {
            summary.labels("/test002", "192.168.129.666", "testServer002").observe(i);
        } else {
            summary.labels("/test001", "192.168.129.666", "testServer002").observe(i);
        }
    }

    public void batchAdd(Integer count, Integer rt) {
        DoubleStream.generate(() -> rt).limit(count).forEach(summary.labels("1", "2")::observe);
    }
}
