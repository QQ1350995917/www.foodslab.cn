package cn.foodslab.controller.cart;

/**
 * Created by Pengwei Ding on 2016-09-05 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 购物车接口
 */
public interface ICartController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 添加产品到购物车
     */
    void create();

    /**
     * 用户接口
     * 更新购物车中的产品数量
     */
    void update();

    /**
     * 读取购物车
     */
    void retrieves();

    /**
     * 用户接口
     * 删除购物车产品
     */
    void delete();

    /**
     * 管理员接口
     * 管理员后台读取用户购物车
     */
    void mRetrieveByUser();
}

