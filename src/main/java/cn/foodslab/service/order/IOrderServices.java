package cn.foodslab.service.order;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 13:53.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderServices {

    /**
     * 读取用户名下所有账户的订单
     * @return
     */
    LinkedList<OrderEntity> retrieveByAccount(String accountId);

    /**
     *
     * @param orderId
     * @return
     */
    OrderEntity retrieveById(String orderId);

    /**
     *
     * @param orderEntity
     * @return
     */
    OrderEntity create(OrderEntity orderEntity);

    /**
     * 用户确认收货
     * @param orderEntity
     * @return
     */
    OrderEntity expressed(OrderEntity orderEntity);

    /**
     *
     * @param status
     * @return
     */
    LinkedList<OrderEntity> mRetrieveByStatus(int status);

    /**
     *
     * @return
     */
    LinkedList<OrderEntity> mRetrieveAll();

    /**
     * 发货
     * @param orderEntity
     * @return
     */
    OrderEntity updateExpress(OrderEntity orderEntity);




}
