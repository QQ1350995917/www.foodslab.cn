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

    LinkedList<CartEntity> retrieveByIds(String... mappingIds);

    LinkedList<CartEntity> retrieveByAccountId(String accountId);

    CartEntity updateAmount(CartEntity cartEntity);

    List<CartEntity> deleteByIds(String... mappingIds);

    CartEntity isExistInCart(CartEntity cartEntity);
}

