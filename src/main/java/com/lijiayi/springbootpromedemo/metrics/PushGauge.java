package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Gauge;

import java.util.Random;

//@Component
public class PushGauge {
    public static Gauge histogram = Gauge.build()
            .name("gauge_template")
            .help("gauge type ")
            .labelNames("method", "entity")
            .register();

    public void gauge(String code) {
        Random random = new Random();
        int i = random.nextInt(100);
        System.out.println("gauge的随机数是: " + i);
        histogram.labels("cyComputer", code).set(i);
    }
}
