package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Gauge;
import org.springframework.stereotype.Component;

@Component
public class PushGauge {
    public static Gauge histogram = Gauge.build()
            .name("gauge_template")
            .help("gauge type ")
            .labelNames("method", "entity")
            .register();

    public void gauge(Double time) {
        histogram.labels("cyComputer", "code").set(time);
    }
}
