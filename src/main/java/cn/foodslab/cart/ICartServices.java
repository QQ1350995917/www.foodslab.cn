package cn.foodslab.cart;

import cn.foodslab.common.response.IResultSet;

import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-05 11:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICartServices {

    IResultSet retrieve();

    IResultSet create(CartEntity cartEntity);

    IResultSet update(CartEntity cartEntity);

    IResultSet delete(List<CartEntity> cartEntities);

    IResultSet isExist(CartEntity cartEntity);
}

