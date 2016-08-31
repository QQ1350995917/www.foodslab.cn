package cn.foodslab.common.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Pengwei Ding on 2016-08-11 11:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 时间格式化工具
 */
public class DateTime {

    /**
     * 把年月日格式的日期转化为秒级数据
     * @param ymdString 年月日字符串，使用中划线分割，如：2016-08-11
     * @return
     * @throws Exception
     */
    public static long parseYMDToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }

    /**
     * 全时间值转化为秒级数据
     * @param ymdString
     * @return
     * @throws Exception
     */
    public static long parseFullTimeToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }


}
