package cn.foodslab.product;

import cn.foodslab.common.utils.DateTime;

/**
 * Created by Pengwei Ding on 2016-08-05 22:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {
    public static void main(String[] args)  throws Exception{
        System.out.println(System.currentTimeMillis());
        String fullTime = DateTime.parseLongToFullTime(1474792322996l);
        System.out.println(fullTime);
    }
}
