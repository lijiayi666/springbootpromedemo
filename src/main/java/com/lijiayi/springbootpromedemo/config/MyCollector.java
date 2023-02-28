package com.lijiayi.springbootpromedemo.config;

import io.prometheus.client.Collector;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyCollector extends Collector {
    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();

        String metricName = "my_counter";

        // Your code to get metrics

        MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("l1"), Arrays.asList("v1"), 4);
        MetricFamilySamples.Sample sample2 = new MetricFamilySamples.Sample(metricName, Arrays.asList("l1", "l2"), Arrays.asList("v1", "v2"), 3);

        MetricFamilySamples samples = new MetricFamilySamples(metricName, Type.COUNTER, "help", Arrays.asList(sample, sample2));

        mfs.add(samples);
        return mfs;
    }
}
