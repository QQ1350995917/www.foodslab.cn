package cn.foodslab.back.login;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 11:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录数据服务层
 */
public interface ILoginServices {

    /**
     * 根据用户名和密码读取符合条件的管理员
     * @param username 用户名
     * @param password 密码
     * @return
     */
    IResultSet retrieveManager(String username,String password);

}
