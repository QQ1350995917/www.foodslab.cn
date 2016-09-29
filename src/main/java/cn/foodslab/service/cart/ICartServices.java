package cn.foodslab.service.cart;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-05 11:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICartServices {

    CartEntity create(CartEntity cartEntity);

    CartEntity retrieveById(String mappingId);

    LinkedList<CartEntity> retrieveCartByAccountId(String accountId);

    /**
     * 读取未支付的format
     * @param formatId
     * @return
     */
    CartEntity retrieveCartByFormatId(String formatId);

    LinkedList<CartEntity> retrieveByIds(String... mappingIds);

    LinkedList<CartEntity> retrieveByOrderId(String orderId);

    LinkedList<CartEntity> retrieveByAccountId(String accountId);

    CartEntity updateAmount(CartEntity cartEntity);

    /**
     * 把购物车中的商品转化为订单商品
     * 更新orderId，pricing，status
     * @param cartEntity
     * @return
     */
    CartEntity attachToOrder(CartEntity cartEntity);

    List<CartEntity> deleteByIds(String... mappingIds);

    CartEntity isExistInCart(CartEntity cartEntity);
}

