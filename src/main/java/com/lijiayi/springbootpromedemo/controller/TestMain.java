package com.lijiayi.springbootpromedemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.lijiayi.springbootpromedemo.entity.Student;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        String studentJson1 = "{\"sex\":\"男\",\"name\":\"李家伊\",\"age\":18}";
        String studentJson2 = "{\"sex\":\"男\",\"name\":\"李狗蛋\",\"age\":18}";
        String carJson1 = "{\"brand\":\"特斯拉\",\"model\":\"model-3\",\"price\":180000}";
        String carJson2 = "{\"brand\":\"特999562325斯拉\",\"model\":\"m21345341odel-Y\",\"price\":1800099995550}";
        long startTime = System.currentTimeMillis();
//        Student student = JSONObject.parseObject(studentJson1, Student.class);
        long mid = System.currentTimeMillis();
//        System.out.println(Thread.currentThread().getName() + " student 第一次解析耗时: " + (mid - startTime));
//        Student student2 = JSONObject.parseObject(studentJson2, Student.class);
        long mid2 = System.currentTimeMillis();
//        System.out.println(Thread.currentThread().getName() + " student 第二次解析耗时: " + (mid2 - mid));
//        Car car1 = JSONObject.parseObject(carJson1, Car.class);
        long mid3 = System.currentTimeMillis();
//        System.out.println(Thread.currentThread().getName() + " car 第1次解析耗时: " + (mid3 - mid2));
//        Car car2 = JSONObject.parseObject(studentJson1, Car.class);
        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " car 第2次解析耗时: " + (end - mid3));
        System.out.println();
//        System.out.println(student.getName());
//        System.out.println(student2.getName());
//        System.out.println(car1.getModel());
//        System.out.println(car2.getModel());
//        Thread.sleep(1000);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                Student student = JSONObject.parseObject(studentJson1, Student.class);
                long mid = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " 解析所用时间: " + (mid - startTime));
            }
        };
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

}
