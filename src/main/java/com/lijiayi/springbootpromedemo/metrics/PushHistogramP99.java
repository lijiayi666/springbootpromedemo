package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Histogram;

//@Component
public class PushHistogramP99 {
    public static Histogram histogram = Histogram.build().buckets(30, 60, 90)
            .name("bulk_request_size_p99")
            .help("histogram of bulk sizes to correlate with duration")
            .labelNames("method", "entity")
            .register();

    public void histogram(Integer rt) {
        histogram.labels("cyComputer", "code").observe(rt);
    }
}
