package com.lijiayi.springbootpromedemo.metrics;

import io.prometheus.client.Histogram;
import org.springframework.stereotype.Component;

import java.util.stream.DoubleStream;

@Component
public class PushHistogram {

    private static String[] labels = {"method", "entity", "le"};
    private static String[] labelValues = {"1", "2", "35"};

    public static Histogram histogram = Histogram.build().buckets(10, 15, 20, 25, 30, 35, 40, 45, 50)
            .name("lijiayi_test_histogram")
            .help("histogram of bulk sizes to correlate with duration")
            .labelNames("method", "entity")
            .register();

    public void histogram(Integer rt) {
        histogram.labels("1", "2").observe(rt);
    }

    public void batchAdd(Integer count, Integer rt) {

        /*long start1 = System.currentTimeMillis();
        IntStream.range(0, count).forEach(i -> histogram.labels("1", "2").observe(rt));
        System.out.println("第一个循环耗时： "  + (System.currentTimeMillis() - start1));*/


//        long start2 = System.currentTimeMillis();
        DoubleStream.generate(() -> rt).limit(count).forEach(histogram.labels("1", "2")::observe);
//        System.out.println("第二个循环耗时： "  + (System.currentTimeMillis() - start2));


//        long start3 = System.currentTimeMillis();
//        DoubleStream.generate(() -> rt).parallel().limit(count).forEach(histogram.labels("1", "2")::observe);
//        System.out.println("第三个循环耗时： "  + (System.currentTimeMillis() - start3));

        /*long start4 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            histogram.labels("1", "2").observe(rt);
        }
        System.out.println("第四个循环耗时： "  + (System.currentTimeMillis() - start4));*/
    }

    public void batchAddTwo(Integer count, Integer rt) {
        System.out.println("----------------------------");
//        histogram.getClass().getDeclaredField()
    }
}
