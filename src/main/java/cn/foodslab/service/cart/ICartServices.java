package cn.foodslab.service.cart;

import cn.foodslab.service.order.OrderEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-05 11:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ICartServices {

    /**
     * 用户接口
     * 判断用户添加的商品是否已经存在于购物车中
     *
     * @param cartEntity 购物车产品对象
     * @return success 购物车产品对象 fail null
     */
    CartEntity exist(CartEntity cartEntity);

    /**
     * 用户接口
     * 向购物车中添加一个产品
     *
     * @param cartEntity 购物车产品对象
     * @return 购物车产品对象 fail null
     */
    CartEntity create(CartEntity cartEntity);

    /**
     * 用户接口
     * 用户修改购物车产品的数量
     *
     * @param cartEntity 购物车产品对象
     * @return 购物车产品对象 fail null
     */
    CartEntity updateAmount(CartEntity cartEntity);

    /**
     * 用户接口
     * 用户删除购物车中的产品
     *
     * @param cartEntity 购物车产品对象
     * @return 购物车产品对象 fail null
     */
    CartEntity deleteById(CartEntity cartEntity);

    /**
     * 用户接口
     * 用户读取购物车产品集合
     *
     * @param accountId 账号ID
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> retrievesByAccountId(String accountId);

    /**
     * 用户接口
     * 用户读取购物车产品集合
     *
     * @param accountIds 账号IDs
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> retrievesByAccountIds(String[] accountIds);

    /**
     * 用户接口
     * 通过购物车ID查找
     *
     * @param accountId 账户ID
     * @param mappingId 购物车产品ID
     * @return 购物车产品对象 fail null
     */
    CartEntity retrieveById(String accountId, String mappingId);

    /**
     * 用户接口
     * 根据IDs批量查找
     *
     * @param accountIds 账户IDs
     * @param mappingIds 产品购物车映射ID
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> retrievesByIds(String[] accountIds, String[] mappingIds);

    /**
     * 用户接口
     * 根据用户IDs和订单IDs查找
     *
     * @param accountIds 账户IDs
     * @param orderId    订单ID
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> retrievesByOrderId(String[] accountIds, String orderId);

    /**
     * 用户接口
     * 用户下单时把购物车中的对应关系绑定到订单上
     * @param orderEntity 订单对象
     * @param mappingIds 映射IDs
     * @return success true fail false
     */
    boolean attachToOrder(OrderEntity orderEntity,String[] mappingIds);

    /**
     * 管理员接口
     * 根据用户IDs和订单IDs分页查找对应关系
     *
     * @param accountIds 账户ID
     * @param orderId    订单ID
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> mRetrievesByOrderId(String[] accountIds, String orderId);

    /**
     * 管理员接口
     * 根据用户IDs和订单IDs分页查找对应关系
     *
     * @param orderId    订单ID
     * @return 购物车产品对象集合 fail null
     */
    LinkedList<CartEntity> mRetrievesByOrderId(String orderId);


}

