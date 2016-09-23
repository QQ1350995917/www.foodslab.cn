package cn.foodslab.controller.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.model.query.QueryPageEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.order.*;
import cn.foodslab.service.product.IProductServices;
import cn.foodslab.service.product.ProductServices;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.service.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderController extends Controller implements IOrderController {
    private IAccountServices iAccountServices = new AccountServices();
    private IOrderServices iOrderServices = new OrderServices();
    private IReceiverService iReceiverService = new ReceiverServices();
    private ICartServices iCartServices = new CartServices();
    private IProductServices iProductServices = new ProductServices();
    private IOrder2ProductServices iOrder2ProductServices = new Order2ProductServices();

    @Override
    public void retrieve() {
        String accountId = getPara("accountId");
        UserEntity userEntity = iAccountServices.retrieveUserByAccountId(accountId);
        IResultSet resultSet = new ResultSet();
        if (userEntity != null) {
            LinkedList<OrderEntity> orderEntities = iOrderServices.retrieve(userEntity.getUserId());
            resultSet.setCode(200);
            resultSet.setData(orderEntities);
        } else {
            resultSet.setCode(500);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String accountId = getPara("accountId");
        String productIds = getPara("productIds");
        if (accountId == null) {
            String senderName = getPara("senderName");
            String senderPhone = getPara("senderPhone");
            String name = getPara("name");
            String phone0 = getPara("phone0");
            String phone1 = getPara("phone1");
            String province = getPara("province");
            String city = getPara("city");
            String county = getPara("county");
            String town = getPara("town");
            String village = getPara("village");
            String append = getPara("append");
            String receiverId = UUID.randomUUID().toString();
            ReceiverEntity receiverEntity = new ReceiverEntity(receiverId, province, city, county, town, village, append, name, phone0, phone1, 1, accountId);
            ReceiverEntity resultReceiver = iReceiverService.create(receiverEntity);
            IResultSet resultSet = new ResultSet();
            if (resultReceiver != null) {
                String orderId = UUID.randomUUID().toString();
                OrderEntity orderEntity = new OrderEntity(orderId, accountId, senderName, senderPhone, receiverEntity.getReceiverId(), 11, 11, 1);
                OrderEntity resultOrder = iOrderServices.create(orderEntity);
                if (resultOrder != null) {
                    LinkedList<Order2ProductEntity> formatMappingEntities = new LinkedList<>();
                    formatMappingEntities.add(new Order2ProductEntity(UUID.randomUUID().toString(), orderId, productIds));
                    List<Order2ProductEntity> orderProductMappingEntities = iOrder2ProductServices.create(formatMappingEntities);
                    if (orderProductMappingEntities != null) {
                        resultSet.setCode(200);
                        resultSet.setData(resultOrder);
                        renderJson(JSON.toJSONString(resultSet));
                    } else {
                        Map<String, String[]> paraMap = this.getParaMap();
                        resultSet.setData(paraMap);
                        resultSet.setMessage("创建订单失败");
                        renderJson(JSON.toJSONString(resultSet));
                    }
                } else {
                    Map<String, String[]> paraMap = this.getParaMap();
                    resultSet.setData(paraMap);
                    resultSet.setMessage("创建订单失败");
                    renderJson(JSON.toJSONString(resultSet));
                }
            } else {
                Map<String, String[]> paraMap = this.getParaMap();
                resultSet.setData(paraMap);
                resultSet.setMessage("创建收货人失败");
                renderJson(JSON.toJSONString(resultSet));
            }
        } else {
            IResultSet resultSet = new ResultSet();
            String receiverId = getPara("receiverId");
            String orderId = UUID.randomUUID().toString();
            float orderTotalPrice = 0.0f;
            LinkedList<Order2ProductEntity> order2ProductEntities = new LinkedList<>();
            String[] split = productIds.split(",");
            for (String productId : split) {
//                CartEntity cartEntity = iCartServices.retrieveById(productId);
//                FormatEntity formatEntity = iProductServices.retrieveTreeByFormatId(cartEntity.getFormatId());
//                Order2ProductEntity order2ProductEntity = new Order2ProductEntity();
//                String mappingId = UUID.randomUUID().toString();
//                order2ProductEntity.setMappingId(mappingId);
//                order2ProductEntity.setOrderId(orderId);
//                order2ProductEntity.setFormatId(formatEntity.getFormatId());
//                order2ProductEntity.setAmount(cartEntity.getAmount());
//                float productTotalPrice = cartEntity.getAmount() * formatEntity.getPricing();
//                order2ProductEntity.setTotalPrice(productTotalPrice);
//                orderTotalPrice = orderTotalPrice + productTotalPrice;
//                order2ProductEntities.add(order2ProductEntity);
            }

            if (iOrder2ProductServices.create(order2ProductEntities) != null) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderId(orderId);
                orderEntity.setAccountId(accountId);
                orderEntity.setReceiverId(receiverId);
                orderEntity.setCost(orderTotalPrice);
                orderEntity.setPostage(0);
                OrderEntity orderCreateResult = iOrderServices.create(orderEntity);
                if (orderCreateResult != null) {
                    String[] strings = iCartServices.deleteByIds(split);
                    if (strings != null) {
                        resultSet.setCode(200);
                        renderJson(JSON.toJSONString(resultSet));
                    } else {
                        resultSet.setCode(500);
                        resultSet.setMessage("删除购物车已经购买选项失败");
                        renderJson(JSON.toJSONString(resultSet));
                    }
                } else {
                    resultSet.setCode(500);
                    resultSet.setMessage("创建订单失败");
                    renderJson(JSON.toJSONString(resultSet));
                }
            } else {
                resultSet.setCode(500);
                resultSet.setMessage("创建订单映射失败");
                renderJson(JSON.toJSONString(resultSet));
            }

        }
    }

    @Override
    public void query() {
        IResultSet iResultSet = new ResultSet();
        String orderId = getPara("orderId");
        if (orderId != null) {
            QueryPageEntity queryPageEntity = new QueryPageEntity();
            OrderEntity orderEntity = iOrderServices.retrieveById(orderId);
            ReceiverEntity receiverEntity = iReceiverService.retrieveById(orderEntity.getReceiverId());

            queryPageEntity.setOrderId(orderEntity.getOrderId());
            queryPageEntity.setOrderTime(orderEntity.getCreateTime());
            String address = receiverEntity.getProvince() + receiverEntity.getCity() + receiverEntity.getCounty() + receiverEntity.getTown() + receiverEntity.getVillage();
            queryPageEntity.setAddress(address);
            queryPageEntity.setName(receiverEntity.getName());
            queryPageEntity.setPhone(receiverEntity.getPhone0());
            queryPageEntity.setExpressName("顺丰快递");
            queryPageEntity.setExpressNumber("1234567890");
            queryPageEntity.setExpressStatus("2016年1月25日 下午7:06:38  北京市|到件|到北京市【北京分拨中心】北京市|发件|北京市【BEX北京昌平区天龙二部】，正发往【北京金盏分拨中心】");

            iResultSet.setCode(200);
            iResultSet.setData(queryPageEntity);
            renderJson(JSON.toJSONString(iResultSet));

        } else {

        }
    }
}
