package com.lijiayi.springbootpromedemo.metrics;

import com.lijiayi.springbootpromedemo.schedule.SendToPushGatewayTask;
import io.prometheus.client.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PushResponseMaxTimer {

    private static Gauge requestMaxTimerGauge = Gauge.build()
            .name("cy_max_timer_gauge")
            .labelNames("instance", "code")
            .help("help -- request duration")
            .register(SendToPushGatewayTask.COLLECTOR_REGISTRY);

    private double maxTime = 0d;

    public synchronized void maxTimer(Gauge.Child child, String code) {
        if (maxTime < child.get()) {
            maxTime = child.get();
            requestMaxTimerGauge.setChild(child, "cyComputer", code);
        }
    }

    public synchronized void reset() {
        Gauge.Child child = new Gauge.Child();
        maxTime = 0d;
        child.set(0d);
        requestMaxTimerGauge.setChild(child, "cyComputer", "000000");
    }

}
