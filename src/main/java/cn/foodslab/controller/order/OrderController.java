package cn.foodslab.controller.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.controller.product.VSeriesEntity;
import cn.foodslab.controller.product.VTypeEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.order.IOrderServices;
import cn.foodslab.service.order.OrderEntity;
import cn.foodslab.service.order.OrderServices;
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

import java.util.Collections;
import java.util.LinkedList;
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
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void index() {

    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        if (vOrderEntity.getSessionId() == null) {
            String receiverId = UUID.randomUUID().toString();
            ReceiverEntity receiverEntity = vOrderEntity.getReceiverEntity();
            receiverEntity.setReceiverId(receiverId);
            /**
             * 保存匿名订单的收货人信息
             */
            ReceiverEntity resultReceiver = iReceiverService.create(receiverEntity);
            if (resultReceiver != null) {
                String orderId = UUID.randomUUID().toString();
                OrderEntity orderEntity = vOrderEntity.getOrderEntity();
                orderEntity.setOrderId(orderId);
                orderEntity.setReceiverId(receiverId);
                orderEntity.setAccountId(vOrderEntity.getSessionId());
                /**
                 * 保存匿名订单的订单信息
                 */
                OrderEntity resultOrder = iOrderServices.create(orderEntity);
                if (resultOrder != null) {
                    CartEntity cartEntity = new CartEntity();
                    String mappingId = UUID.randomUUID().toString();
                    cartEntity.setMappingId(mappingId);
                    cartEntity.setFormatId(vOrderEntity.getProductIds());
                    cartEntity.setAmount(1);
                    cartEntity.setPricing(0);
                    cartEntity.setOrderId(resultOrder.getOrderId());
                    cartEntity.setStatus(2);
                    CartEntity resultCartEntity = iCartServices.create(cartEntity);
                    if (resultCartEntity != null) {
                        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), orderEntity, "success");
                        renderJson(JSON.toJSONString(iResultSet));
                    }
                } else {

                }
            } else {

            }
        } else {
            String orderId = UUID.randomUUID().toString();
            float orderTotalPrice = 0.0f;
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setAccountId(vOrderEntity.getSessionId());
            orderEntity.setOrderId(orderId);
            orderEntity.setCost(orderTotalPrice);
            orderEntity.setPostage(0);
            orderEntity.setReceiverId(vOrderEntity.getReceiverId());
            OrderEntity orderCreateResult = iOrderServices.create(orderEntity);
            if (orderCreateResult != null) {
                String[] split = vOrderEntity.getProductIds().split(",");
//                for (String formatId : split) {
//                    CartEntity cartEntity = iCartServices.retrieveById(formatId);
//                    cartEntity.setOrderId(orderId);
//                    cartEntity.setStatus(2);
//                    iCartServices.attachToOrder(cartEntity);
//                }
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), orderEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            } else {

            }
        }
    }

    @Override
    public void expressed() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        OrderEntity orderEntity = vOrderEntity.getOrderEntity();
        OrderEntity result = iOrderServices.expressed(orderEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        UserEntity userEntity = iAccountServices.retrieveUserByAccountId(vOrderEntity.getSessionId());
        if (userEntity == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(userEntity.getUserId());
            String[] accountIds = new String[accountEntities.size()];
            for (int index = 0; index < accountEntities.size(); index++) {
                accountIds[index] = accountEntities.get(index).getAccountId();
            }

            LinkedList<VOrderEntity> result = new LinkedList<>();
            LinkedList<OrderEntity> orderEntities = iOrderServices.retrievesByAccounts(accountIds, 0, 0, 0);
            for (int index = 0; index < orderEntities.size(); index++) {
                OrderEntity orderEntity = orderEntities.get(index);
                VOrderEntity vOrderEntity1 = new VOrderEntity(orderEntity);
                LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
                LinkedList<CartEntity> cartEntities = iCartServices.retrievesByOrderId(accountIds, orderEntity.getOrderId());
                for (CartEntity cartEntity : cartEntities) {
                    FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                    TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                    SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                    VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                    VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                    VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                    vTypeEntity.setParent(vSeriesEntity);
                    vFormatEntity.setParent(vTypeEntity);
                    vFormatEntities.add(vFormatEntity);
                }
                vOrderEntity1.setFormatEntities(vFormatEntities);
                result.add(vOrderEntity1);
            }
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            iResultSet.setData(result);
            iResultSet.setMessage("success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }


    @Override
    public void query() {
        IResultSet iResultSet = new ResultSet();
        String orderId = getPara("orderId");
        if (orderId != null) {
            QueryPageEntity queryPageEntity = new QueryPageEntity();
            LinkedList<OrderEntity> query = iOrderServices.query(orderId, 0, 0);
            OrderEntity orderEntity = query.get(0);
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

    @Override
    public void mRetrievesByUser() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(vUserEntity.getUserId());
        String[] accountIds = new String[accountEntities.size()];
        for (int index = 0; index < accountEntities.size(); index++) {
            accountIds[index] = accountEntities.get(index).getAccountId();
        }
        LinkedList<VOrderEntity> result = new LinkedList<>();
        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrievesByUser(accountIds, 0, 0, 0);
        for (int index = 0; index < orderEntities.size(); index++) {
            OrderEntity orderEntity = orderEntities.get(index);
            VOrderEntity vOrderEntity = new VOrderEntity(orderEntity);
            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
            LinkedList<CartEntity> cartEntities = iCartServices.mRetrievesByOrderId(accountIds, orderEntity.getOrderId());
            for (CartEntity cartEntity : cartEntities) {
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                vFormatEntities.add(vFormatEntity);
            }
            vOrderEntity.setFormatEntities(vFormatEntities);
            result.add(vOrderEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(result);
        iResultSet.setMessage("success");
        renderJson(JSON.toJSONString(iResultSet));
    }

//    @Override
//    public void mRetrieveUnExpress() {
//        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieveByStatus(1);
//        LinkedList<VOrderEntity> vOrderEntities = new LinkedList<>();
//        for (OrderEntity orderEntity : orderEntities) {
//            VOrderEntity result = new VOrderEntity(orderEntity);
//            LinkedList<CartEntity> cartEntities = iCartServices.retrieveByOrderId(orderEntity.getOrderId());
//            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
//            for (CartEntity cartEntity : cartEntities) {
//                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
//                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
//                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
//                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
//                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
//                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
//                vTypeEntity.setParent(vSeriesEntity);
//                vFormatEntity.setParent(vTypeEntity);
//                vFormatEntities.add(vFormatEntity);
//            }
//            result.setFormatEntities(vFormatEntities);
//            vOrderEntities.add(result);
//        }
//        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntities, "success");
//        renderJson(JSON.toJSONString(iResultSet));
//    }

//    @Override
//    public void mRetrieveExpressing() {
//        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieveByStatus(2);
//        LinkedList<VOrderEntity> vOrderEntities = new LinkedList<>();
//        for (OrderEntity orderEntity : orderEntities) {
//            VOrderEntity result = new VOrderEntity(orderEntity);
//            LinkedList<CartEntity> cartEntities = iCartServices.retrieveByOrderId(orderEntity.getOrderId());
//            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
//            for (CartEntity cartEntity : cartEntities) {
//                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
//                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
//                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
//                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
//                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
//                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
//                vTypeEntity.setParent(vSeriesEntity);
//                vFormatEntity.setParent(vTypeEntity);
//                vFormatEntities.add(vFormatEntity);
//            }
//            result.setFormatEntities(vFormatEntities);
//            vOrderEntities.add(result);
//        }
//        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntities, "success");
//        renderJson(JSON.toJSONString(iResultSet));
//    }
//
//    @Override
//    public void mRetrieveExpressed() {
//        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieveByStatus(3);
//        LinkedList<VOrderEntity> vOrderEntities = new LinkedList<>();
//        for (OrderEntity orderEntity : orderEntities) {
//            VOrderEntity result = new VOrderEntity(orderEntity);
//            LinkedList<CartEntity> cartEntities = iCartServices.retrieveByOrderId(orderEntity.getOrderId());
//            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
//            for (CartEntity cartEntity : cartEntities) {
//                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
//                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
//                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
//                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
//                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
//                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
//                vTypeEntity.setParent(vSeriesEntity);
//                vFormatEntity.setParent(vTypeEntity);
//                vFormatEntities.add(vFormatEntity);
//            }
//            result.setFormatEntities(vFormatEntities);
//            vOrderEntities.add(result);
//        }
//        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntities, "success");
//        renderJson(JSON.toJSONString(iResultSet));
//    }

    @Override
    public void mRetrieves() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieves(vOrderEntity.getStatus(), 0, 0);
        LinkedList<VOrderEntity> vOrderEntities = new LinkedList<>();
        for (OrderEntity orderEntity : orderEntities) {
            VOrderEntity result = new VOrderEntity(orderEntity);
            LinkedList<CartEntity> cartEntities = iCartServices.mRetrievesByOrderId(orderEntity.getOrderId());
            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
            for (CartEntity cartEntity : cartEntities) {
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                vFormatEntities.add(vFormatEntity);
            }
            result.setFormatEntities(vFormatEntities);
            vOrderEntities.add(result);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mExpressing() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        OrderEntity orderEntity = vOrderEntity.getOrderEntity();
        OrderEntity result = iOrderServices.mExpressing(orderEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }
}
