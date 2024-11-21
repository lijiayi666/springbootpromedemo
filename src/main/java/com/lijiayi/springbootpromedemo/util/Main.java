package com.lijiayi.springbootpromedemo.util;

import java.text.DecimalFormat;
import java.util.Random;

public class Main {
    // 生成随机地区码
    private static String generateRegionCode() {
        Random random = new Random();
        // 地区码的范围是110000到659000
        int regionCode = 110000 + random.nextInt(549000);
        DecimalFormat df = new DecimalFormat("000000");
        return df.format(regionCode);
    }

    // 生成随机生日
    private static String generateBirthday() {
        Random random = new Random();
        // 生日的范围为1950年到2003年
        int year = 1950 + random.nextInt(54);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28); // 为简化起见，假设每个月的最大天数为28
        DecimalFormat df = new DecimalFormat("00");
        return String.valueOf(year) + df.format(month) + df.format(day);
    }

    // 生成顺序码
    private static String generateSequenceCode() {
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("000");
        return df.format(random.nextInt(1000));
    }

    // 计算校验码
    private static String generateVerifyCode(String id17) {
        char[] chars = id17.toCharArray();
        int[] ints = new int[17];
        int[] coefficient = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] verifyCodes = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        for (int i = 0; i < chars.length; i++) {
            ints[i] = Integer.parseInt(String.valueOf(chars[i]));
            sum += ints[i] * coefficient[i];
        }
        int remainder = sum % 11;
        return String.valueOf(verifyCodes[remainder]);
    }

    // 生成18位身份证号码
    private static String generateIDCard() {
        String regionCode = generateRegionCode();
        String birthday = generateBirthday();
        String sequenceCode = generateSequenceCode();
        String id17 = regionCode + birthday + sequenceCode;
        String verifyCode = generateVerifyCode(id17);
        return id17 + verifyCode;
    }

    public static void main(String[] args) {
        String idCard = generateIDCard();
        System.out.println("生成的身份证号码：" + idCard);
    }
}


