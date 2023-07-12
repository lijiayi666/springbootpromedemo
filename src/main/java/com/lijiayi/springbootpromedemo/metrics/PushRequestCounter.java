package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Counter;
import org.springframework.stereotype.Component;

@Component
public class PushRequestCounter {

    public static Counter requestTimesCounter = Counter.build()
            .name("cy_request_counter")
            .labelNames("instance", "code")
            .help("help -- request counter")
//            .register(TestController.COLLECTOR_REGISTRY);
            .register();

    public void count(String code, Double inc) {
        requestTimesCounter.labels("cyComputer", code).inc(inc);
    }
}
