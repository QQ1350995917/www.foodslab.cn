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
    LinkedList<OrderEntity> retrieve(String userId);

    OrderEntity create(OrderEntity orderEntity);

}
