package cn.foodslab.back.init;

import cn.foodslab.back.manager.ManagerDao;
import cn.foodslab.back.manager.ManagerEntity;

/**
 * Created by Pengwei Ding on 2016-07-28 20:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 初始化超级管理员
 */
public class InitManager {
    private static final String id = "15b1255a-a808-437f-9f63-31dc3a0cfa55";
    public static void main(String[] args) throws Exception {
        ManagerEntity admin = new ManagerEntity(id, "admin", "123456", 0, 0, 1, id);
        ManagerDao managerDao = new ManagerDao();
        boolean result = managerDao.initManager(admin);
        if (result) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }
}
