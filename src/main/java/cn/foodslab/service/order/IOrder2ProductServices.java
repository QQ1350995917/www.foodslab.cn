package cn.foodslab.service.order;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 17:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrder2ProductServices {
    LinkedList<Order2ProductEntity> retrieveByOrder(String orderId);
    LinkedList<Order2ProductEntity> retrieve(String mapping);
    LinkedList<Order2ProductEntity> create(LinkedList<Order2ProductEntity> formatMappingEntities);
}
