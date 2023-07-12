package com.lijiayi.springbootpromedemo.metrics;

import com.lijiayi.springbootpromedemo.controller.TestController;
import io.prometheus.client.Histogram;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.DoubleStream;

@Component
public class PushHistogram {


    public static Histogram histogram = Histogram.build()
//            .buckets(10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95)
            .buckets(10, 50, 80,95)
            .name("lijiayi_test_histogram_0322")
            .help("histogram of bulk sizes to correlate with duration lijiayi666")
            .labelNames("url", "ip", "serverName")
            .register(TestController.COLLECTOR_REGISTRY);

    public void histogram() {
        Random random = new Random();
        int i = random.nextInt(100);
        /*if (i > 30 && i <= 70) {
            histogram.labels("/test001", "192.168.129.666", "testServer001").observe(i);
        } else if (i > 70 && i <= 90){
            histogram.labels("/test002", "192.168.129.666", "testServer001").observe(i);
        } else if (i > 90) {
            histogram.labels("/test002", "192.168.129.666", "testServer002").observe(i);
        } else {
            histogram.labels("/test001", "192.168.129.666", "testServer002").observe(i);
        }*/
        histogram.labels("/test001", "192.168.129.666", "testServer002").observe(i);
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
