package cn.foodslab.cart;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-05 11:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICartServices {

    LinkedList<Map<String,Object>> retrieve();

    CartEntity create(CartEntity cartEntity);

    CartEntity update(CartEntity cartEntity);

    List<CartEntity> delete(List<CartEntity> cartEntities);

    CartEntity isExist(CartEntity cartEntity);
}

