package cn.foodslab.controller.order;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.manager.VManagerEntity;
import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.controller.product.VSeriesEntity;
import cn.foodslab.controller.product.VTypeEntity;
import cn.foodslab.controller.receiver.VReceiverEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.MenuInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

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
    @Before(SessionInterceptor.class)
    public void create() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();

        requestVOrderEntity.setStatus(1);
        String orderId = UUID.randomUUID().toString();
        float orderTotalPrice = 0.0f;
        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        requestVOrderEntity.setAccountId(vUserEntity.getChildren().get(0).getAccountId());
        requestVOrderEntity.setOrderId(orderId);
        requestVOrderEntity.setCost(orderTotalPrice);
        requestVOrderEntity.setPostage(0);
        OrderEntity createOrderEntity = iOrderServices.create(requestVOrderEntity);
        if (createOrderEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        boolean attachToOrder = iCartServices.attachToOrder(createOrderEntity, requestVOrderEntity.getProductIds());
        if (!attachToOrder) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VOrderEntity(createOrderEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
    }

    @Override
    public void createAnonymous() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        requestVOrderEntity.setStatus(1);
        /**
         * 保存匿名订单的收货人信息
         */
        VReceiverEntity receiverEntity = requestVOrderEntity.getReceiver();
        receiverEntity.setReceiverId(UUID.randomUUID().toString());
        ReceiverEntity createReceiver = iReceiverService.create(null, receiverEntity);
        if (createReceiver == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }

        String orderId = UUID.randomUUID().toString();
        requestVOrderEntity.setOrderId(orderId);
        requestVOrderEntity.setReceiverId(createReceiver.getReceiverId());
        /**
         * 保存匿名订单的订单信息
         */
        OrderEntity createOrderEntity = iOrderServices.create(requestVOrderEntity);
        if (createOrderEntity == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }
        CartEntity cartEntity = new CartEntity();
        String mappingId = UUID.randomUUID().toString();
        cartEntity.setMappingId(mappingId);
        cartEntity.setFormatId(requestVOrderEntity.getProductIds()[0]);
        cartEntity.setAmount(1);
        cartEntity.setPricing(0);
        cartEntity.setOrderId(createOrderEntity.getOrderId());
        cartEntity.setStatus(2);
        CartEntity resultCartEntity = iCartServices.create(cartEntity);
        if (resultCartEntity == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VOrderEntity(createOrderEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));//TODO 裁剪返回参数
    }

    @Before(SessionInterceptor.class)
    @Override
    public void expressed() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkOrderIdParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        OrderEntity result = iOrderServices.expressed(vUserEntity.getChildren(), requestVOrderEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Before(SessionInterceptor.class)
    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        VUserEntity vUserEntity = (VUserEntity) SessionContext.getSession(requestVOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        LinkedList<OrderEntity> orderEntities = iOrderServices.retrievesByAccounts(vUserEntity.getChildren(), 0, 0, 0);
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
        for (int index = 0; index < orderEntities.size(); index++) {
            OrderEntity orderEntity = orderEntities.get(index);
            VOrderEntity vOrderEntity1 = new VOrderEntity(orderEntity);
            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
            LinkedList<CartEntity> cartEntities = iCartServices.retrievesByOrderId(vUserEntity.getChildren(), orderEntity.getOrderId());
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
            responseVOrderEntities.add(vOrderEntity1);
        }

        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVOrderEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime","formatEntities"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }


    @Override
    public void query() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVOrderEntity.checkOrderIdParams()) {
            QueryPageEntity queryPageEntity = new QueryPageEntity();
            LinkedList<OrderEntity> query = iOrderServices.query(requestVOrderEntity.getOrderId(), 0, 0);
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

            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
            iResultSet.setData(queryPageEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrievesByUser() {
        String params = this.getPara("p");
        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVManagerEntity.checkUserParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(requestVManagerEntity.getUser().getUserId());
        if (accountEntities == null || accountEntities.size() < 1) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrievesByUser(accountEntities, 0, 0, 0);
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VManagerEntity.class, "user")));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
        for (int index = 0; index < orderEntities.size(); index++) {
            OrderEntity orderEntity = orderEntities.get(index);
            VOrderEntity vOrderEntity = new VOrderEntity(orderEntity);
            LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
            LinkedList<CartEntity> cartEntities = iCartServices.mRetrievesByOrderId(accountEntities, orderEntity.getOrderId());
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
            responseVOrderEntities.add(vOrderEntity);
        }
        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVOrderEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime","formatEntities"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkStatusParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "status")));
            return;
        }

        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrieves(requestVOrderEntity.getStatus(), 0, 0);
        if (orderEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "status")));
            return;
        }

        LinkedList<VOrderEntity> responseVOrderEntities = new LinkedList<>();
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
            responseVOrderEntities.add(result);
        }
        if (responseVOrderEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVOrderEntities);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VOrderEntity.class, "orderId", "accountId", "senderName", "senderPhone", "receiverId", "cost", "postage", "status", "expressLabel", "expressNumber", "createTime","formatEntities"),
                new SimplePropertyPreFilter(VFormatEntity.class, "typeId", "formatId", "label", "meta", "amount", "amountMeta", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "seriesId", "typeId", "label", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mExpressing() {
        String params = this.getPara("p");
        VOrderEntity requestVOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVOrderEntity.checkExpressParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
            return;
        }
        OrderEntity result = iOrderServices.mExpressing(requestVOrderEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVOrderEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVOrderEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VOrderEntity.class, "expressLabel", "expressNumber")));
    }

    @Override
    @Before({SessionInterceptor.class, MenuInterceptor.class})
    public void mQuery() {

    }
}
