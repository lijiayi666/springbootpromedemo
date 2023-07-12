package com.lijiayi.springbootpromedemo.controller;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMain {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
//        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("test", 1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "线程第一次获取 test的结果是：" + map.get("test"));
            try {
                countDownLatch.countDown();
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("test", 11);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "线程第二次获取 test的结果是：" + map.get("test"));
        });

        executorService.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "***线程第一次获取 test的结果是：" + map.get("test"));
            try {
                countDownLatch.countDown();
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("test", 22);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "***线程第二次获取 test的结果是：" + map.get("test"));
        });
    }
}
