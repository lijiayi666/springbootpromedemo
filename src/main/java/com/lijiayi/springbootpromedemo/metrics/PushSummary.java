package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Summary;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.DoubleStream;

@Component
public class PushSummary {
    public static Summary summary = Summary.build()
            .name("summary_template")
            .help("histogram of bulk sizes to correlate with duration")
            .labelNames("method", "entity")
            .quantile(0.99, 0.01)
            .quantile(0.95, 0.01)
            .quantile(0.50, 0.01)
            .register();

    public void summary(String code) {
        Random random = new Random();
        int i = random.nextInt(100);
        System.out.println("summary的随机数是: " + i);
        summary.labels("cyComputer", code).observe(i);
    }

    public void batchAdd(Integer count, Integer rt) {
        DoubleStream.generate(() -> rt).limit(count).forEach(summary.labels("1", "2")::observe);
    }
}
