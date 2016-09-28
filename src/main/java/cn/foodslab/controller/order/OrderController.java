package cn.foodslab.controller.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.model.order.VOrderEntity;
import cn.foodslab.model.product.VFormatEntity;
import cn.foodslab.model.product.VSeriesEntity;
import cn.foodslab.model.product.VTypeEntity;
import cn.foodslab.model.query.QueryPageEntity;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.order.*;
import cn.foodslab.service.product.*;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.service.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.List;
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
    private IOrder2ProductServices iOrder2ProductServices = new Order2ProductServices();
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        UserEntity userEntity = iAccountServices.retrieveUserByAccountId(vOrderEntity.getSessionId());
        if (userEntity == null) {
            IResultSet iResultSet = new ResultSet(3000, vOrderEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveAccountsByUserId(userEntity.getUserId());
            LinkedList<VOrderEntity> results = new LinkedList<>();
            for (AccountEntity accountEntity : accountEntities) {
                LinkedList<OrderEntity> orderEntities = iOrderServices.retrieveByAccount(accountEntity.getAccountId());
                for (OrderEntity orderEntity : orderEntities) {
                    LinkedList<Order2ProductEntity> order2ProductEntities = iOrder2ProductServices.retrieveByOrder(orderEntity.getOrderId());
                    LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
                    for (Order2ProductEntity order2ProductEntity:order2ProductEntities){
                        FormatEntity formatEntity = iFormatServices.retrieveById(order2ProductEntity.getFormatId());
                        TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                        SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                        VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                        VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                        VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                        vTypeEntity.setParent(vSeriesEntity);
                        vFormatEntity.setParent(vTypeEntity);
                        vFormatEntities.add(vFormatEntity);
                    }
                    VOrderEntity result = new VOrderEntity(orderEntity);
                    result.setFormatEntities(vFormatEntities);
                    results.add(result);
                }
            }
            IResultSet iResultSet = new ResultSet(3050, results, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        if (vOrderEntity.getSessionId() == null){
            String receiverId = UUID.randomUUID().toString();
            ReceiverEntity receiverEntity = vOrderEntity.getReceiverEntity();
            receiverEntity.setReceiverId(receiverId);
            ReceiverEntity resultReceiver = iReceiverService.create(receiverEntity);
            if (resultReceiver != null) {
                String orderId = UUID.randomUUID().toString();
                OrderEntity orderEntity = vOrderEntity.getOrderEntity();
                orderEntity.setOrderId(orderId);
                orderEntity.setReceiverId(receiverId);
                orderEntity.setAccountId(vOrderEntity.getSessionId());
                OrderEntity resultOrder = iOrderServices.create(orderEntity);
                if (resultOrder != null) {
                    LinkedList<Order2ProductEntity> formatMappingEntities = new LinkedList<>();
                    formatMappingEntities.add(new Order2ProductEntity(UUID.randomUUID().toString(), orderId, vOrderEntity.getProductIds()));
                    List<Order2ProductEntity> orderProductMappingEntities = iOrder2ProductServices.create(formatMappingEntities);
                    if (orderProductMappingEntities != null) {
                        IResultSet iResultSet = new ResultSet(3050, orderEntity, "success");
                        renderJson(JSON.toJSONString(iResultSet));
                    } else {

                    }
                } else {

                }
            } else {

            }
        }else {
            String orderId = UUID.randomUUID().toString();
            float orderTotalPrice = 0.0f;
            LinkedList<Order2ProductEntity> order2ProductEntities = new LinkedList<>();
            String[] split = vOrderEntity.getProductIds().split(",");
            for (String productId : split) {
                CartEntity cartEntity = iCartServices.retrieveById(productId);
                Order2ProductEntity order2ProductEntity = new Order2ProductEntity();
                String mappingId = UUID.randomUUID().toString();
                order2ProductEntity.setMappingId(mappingId);
                order2ProductEntity.setOrderId(orderId);
                order2ProductEntity.setFormatId(cartEntity.getFormatId());
                order2ProductEntity.setAmount(cartEntity.getAmount());
                float productTotalPrice = cartEntity.getAmount();
                order2ProductEntity.setTotalPrice(productTotalPrice);
                orderTotalPrice = orderTotalPrice + productTotalPrice;
                order2ProductEntities.add(order2ProductEntity);
            }

            if (iOrder2ProductServices.create(order2ProductEntities) != null) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setAccountId(vOrderEntity.getSessionId());
                orderEntity.setOrderId(orderId);
                orderEntity.setCost(orderTotalPrice);
                orderEntity.setPostage(0);
                orderEntity.setReceiverId(vOrderEntity.getReceiverId());
                OrderEntity orderCreateResult = iOrderServices.create(orderEntity);
                if (orderCreateResult != null) {
                    List<CartEntity> cartEntities = iCartServices.deleteByIds(split);
                    if (cartEntities != null) {
                        IResultSet iResultSet = new ResultSet(3050, orderEntity, "success");
                        renderJson(JSON.toJSONString(iResultSet));
                    } else {
                    }
                } else {
                }
            } else {
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
