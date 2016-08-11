package cn.foodslab.utils;

/**
 * Created by Pengwei Ding on 2016-08-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class DateTimeTest {

    public static void main(String[] args) throws Exception {
        String dataTime = "2016-06-06";
        long l = DateTime.parseYMDToLong(dataTime);
        System.out.println(l);
    }
}
