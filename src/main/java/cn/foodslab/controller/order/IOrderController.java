package cn.foodslab.controller.order;

/**
 * Created by Pengwei Ding on 2016-08-31 13:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 用户创建一个订单
     */
    void create();

    /**
     * 用户接口
     * 用户确认收货
     */
    void expressed();

    /**
     * 用户接口
     * 用户读取自己的订单列表
     */
    void retrieves();

    /**
     * 用户接口
     * 匿名用户订单查询
     */
    void query();

    /**
     * 管理员接口
     * 根据状态分页查找所有订单
     */
    void mRetrieves();

    /**
     * 管理员接口
     * 根据用户分页分状态查询订单
     */
    void mRetrievesByUser();

    /**
     * 管理员接口
     * 根据订单发货
     */
    void mExpressing();

}
