package com.lijiayi.springbootpromedemo.metrics;

import com.lijiayi.springbootpromedemo.schedule.SendToPushGatewayTask;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.stereotype.Component;

@Component
public class PushResponseAvgTimer {

    private static Counter requestTotalTimeCounter = Counter.build()
            .name("cy_total_time_counter")
            .labelNames("instance", "code")
            .help("help -- request duration")
            .register(SendToPushGatewayTask.COLLECTOR_REGISTRY);

    public void count(Gauge.Child child, String code) {
        requestTotalTimeCounter.labels("cyComputer", code).inc(child.get());
    }

}
