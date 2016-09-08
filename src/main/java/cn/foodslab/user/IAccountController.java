package cn.foodslab.user;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountController {

    void index();

    /**
     * 通过用户的电话号码读取账户
     */
    void retrieve();

    /**
     * 创建新的账户同时创建用户ID
     */
    void create();

    /**
     * 更新账户
     */
    void update();

    /**
     * 禁用账户
     * 管理员接口
     */
    void block();

    /**
     * 把两个账户绑定到同一个用户
     */
    void bind();

}
