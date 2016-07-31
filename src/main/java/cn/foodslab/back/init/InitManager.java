package cn.foodslab.back.init;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Pengwei Ding on 2016-07-28 20:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 初始化超级管理员
 */
public class InitManager extends InitBase{
    public static final String id = "15b1255a-a808-437f-9f63-31dc3a0cfa58";
    public static void main(String[] args) throws Exception {
        boolean result = false;
        try {
            Record manager = new Record()
                    .set("managerId", id)
                    .set("username", "dingpengwei")
                    .set("password", "123456")
                    .set("level", 0)
                    .set("status", 1)
                    .set("pId", id);
            result = Db.save("manager", manager);
        }catch (Exception e){
            System.out.println(e.toString());
        }
        if (result) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }
}
