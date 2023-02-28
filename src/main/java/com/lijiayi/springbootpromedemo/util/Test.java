package com.lijiayi.springbootpromedemo.util;

import org.slf4j.MDC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {


    /**
     * 传入两个时间范围，返回这两个时间范围内的所有日期，并保存在一个集合中
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static List<String> findEveryDay(String beginTime, String endTime)
            throws Exception {
        //创建一个放所有日期的集合
        List<String> dates = new ArrayList();

        //创建时间解析对象规定解析格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        //将传入的时间解析成Date类型,相当于格式化
        Date dBegin = sdf.parse(beginTime);
        //将传入的时间解析成Date类型,相当于格式化
        Date dEnd = sdf.parse(endTime);

        //将格式化后的第一天添加进集合
        dates.add(sdf.format(dBegin));

        //使用本地的时区和区域获取日历
        Calendar calBegin = Calendar.getInstance();

        //传入起始时间将此日历设置为起始日历
        calBegin.setTime(dBegin);

        //判断结束日期前一天是否在起始日历的日期之后
        while (dEnd.after(calBegin.getTime())) {

            //根据日历的规则:月份中的每一天，为起始日历加一天
            calBegin.add(Calendar.DAY_OF_MONTH, 1);

            //得到的每一天就添加进集合
            dates.add(sdf.format(calBegin.getTime()));
            //如果当前的起始日历超过结束日期后,就结束循环
        }
        return dates;
    }

    public static void main(String[] args) throws Exception {
        MDC.put("2131", "李家伊666 " + Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    MDC.put("2131", "李家伊666 " + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + " " + MDC.get("2131"));
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        System.out.println(Thread.currentThread().getName() + " " + MDC.get("2131"));
    }

    public static Date dateAddOne(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR_OF_DAY, 1); //把日期往后增加一小时,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat yyyyMMddHH = new SimpleDateFormat("yyyyMMddHH");
        String format = yyyyMMddHH.format(date);
        try {
            date = yyyyMMddHH.parse(format);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

}
