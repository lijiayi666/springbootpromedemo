package com.lijiayi.springbootpromedemo.metrics;

import com.lijiayi.springbootpromedemo.schedule.SendToPushGatewayTask;
import io.prometheus.client.Counter;

//@Component
public class PushRequestCounter {

    public static Counter requestTimesCounter = Counter.build()
            .name("cy_request_counter")
            .labelNames("instance", "code")
            .help("help -- request counter")
            .register(SendToPushGatewayTask.COLLECTOR_REGISTRY);

    public void count(String code) {
        requestTimesCounter.labels("cyComputer", code).inc();
    }
}
