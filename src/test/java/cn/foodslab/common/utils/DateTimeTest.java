package cn.foodslab.common.utils;

/**
 * Created by Pengwei Ding on 2016-08-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class DateTimeTest {

    public static void main(String[] args) throws Exception {
        String dataTime = "2016-12-31";
        long l = DateTime.parseYMDToLong(dataTime);
        System.out.println(l);

        String s = DateTime.parseLongToFullTime(1470182500000l);
        System.out.println(s);
    }
}
