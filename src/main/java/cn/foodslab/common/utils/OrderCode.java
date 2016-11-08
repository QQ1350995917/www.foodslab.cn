package cn.foodslab.common.utils;

import java.util.*;

/**
 * Created by Pengwei Ding on 2016-11-01 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 生成订单编号
 * 要求：1：非零开头的十一位数字字符串，2：防止预测生成总量和单位时间生成总量，3：尽可能不重复，4：速度每秒最低生成100个
 */
public class OrderCode {
    public static synchronized String gen() {
        Random random = new Random();
        return (random.nextInt(9) + 1) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + ""
                + random.nextInt(9) + "";
    }
}
