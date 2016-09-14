package cn.foodslab.controller.order;

/**
 * Created by Pengwei Ding on 2016-08-31 13:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderController {

    /**
     * 用户读取自己的订单列表
     */
    void retrieve();

    /**
     * 用户创建一个订单
     */
    void create();

    /**
     * 匿名用户订单查询
     */
    void query();
}
