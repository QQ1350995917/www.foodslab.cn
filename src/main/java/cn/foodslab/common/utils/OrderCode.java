package cn.foodslab.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Pengwei Ding on 2016-11-01 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderCode {
    private static Date CURRENT_DATE = new Date();
    private static int CURRENT_BASE_CODE = 0;
    static{
        String suffix = String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                + String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE));
    }

    public static synchronized String gen() {
        if (!new Date().equals(CURRENT_DATE)) {
            //每天更换新的基数号码
            CURRENT_DATE = new Date();
            CURRENT_BASE_CODE = 0;
        }
        Random random = new Random(9999);
        int nextInt = random.nextInt();
        int currentOrderCode = nextInt + CURRENT_BASE_CODE;
        CURRENT_BASE_CODE ++;
        return currentOrderCode + "";
    }
}
