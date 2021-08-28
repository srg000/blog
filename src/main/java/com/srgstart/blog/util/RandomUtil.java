package com.srgstart.blog.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author  srgstart
 * @Create 2021/3/31 21:13
 * @Description TODO
 */
public class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOUR_DF = new DecimalFormat("0000");

    private static final DecimalFormat SIX_DF = new DecimalFormat("000000");

    /**
     * 生成四位验证码
     * @return 验证码
     */
    public static String getFourBitRandom() {
        return FOUR_DF.format(RANDOM.nextInt(10000));
    }

    /**
     * 生成六位验证码
     * @return 验证码
     */
    public static String getSixBitRandom() {
        return SIX_DF.format(RANDOM.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     * @param list :list
     * @param n : n个数据
     * @return ：ArrayList
     */
    public static ArrayList getRandom(List list, int n) {

        Random random = new Random();

        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {

            int number = random.nextInt(100) + 1;

            hashMap.put(number, i);
        }

        // 从HashMap导入数组
        Object[] robjs = hashMap.values().toArray();

        ArrayList r = new ArrayList();

        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
            System.out.print(list.get((int) robjs[i]) + "\t");
        }
        System.out.print("\n");
        return r;
    }
}