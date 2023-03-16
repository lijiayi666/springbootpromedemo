package com.lijiayi.springbootpromedemo.controller;

import java.util.Scanner;

public class TestMain {
    private static final String[] CHINESE_NUMBERS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] CHINESE_UNITS = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入整数的字符串表达式：");
        String input = scanner.nextLine();
        String output = convertToChinese(input);
        System.out.println(output);
    }

    private static String convertToChinese(String input) {
        StringBuilder sb = new StringBuilder();
        boolean hasMinus = false;
        if (input.startsWith("-")) {
            hasMinus = true;
            input = input.substring(1);
        }
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("输入表达式不是整数的字符串表达式");
        }
        int len = input.length();
        if (len > CHINESE_UNITS.length * 4) {
            throw new IllegalArgumentException("输入整数超过范围");
        }
        int zeroCount = 0;
        boolean needZero = false;
        for (int i = 0; i < len; i++) {
            int num = input.charAt(i) - '0';
            int unitIndex = (len - i - 1) % CHINESE_UNITS.length;
            if (num == 0) {
                zeroCount++;
                if (unitIndex == CHINESE_UNITS.length - 1 || unitIndex == 4) {
                    needZero = true;
                }
            } else {
                if (needZero) {
                    sb.append(CHINESE_NUMBERS[0]);
                    needZero = false;
                }
                if (zeroCount > 0) {
                    sb.append(CHINESE_NUMBERS[0]);
                    zeroCount = 0;
                }
                sb.append(CHINESE_NUMBERS[num]);
                sb.append(CHINESE_UNITS[unitIndex]);
            }
            if (unitIndex == 0 && needZero) {
                sb.append(CHINESE_NUMBERS[0]);
                needZero = false;
            }
        }
        if (sb.length() == 0) {
            sb.append(CHINESE_NUMBERS[0]);
        }
        if (hasMinus) {
            sb.insert(0, "负");
        }
        // 如果末尾有零，去掉末尾的零
        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '零') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
