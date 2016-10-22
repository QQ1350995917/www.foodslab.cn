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
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        vOrderEntity.setStatus(1);
        String orderId = UUID.randomUUID().toString();
        float orderTotalPrice = 0.0f;
        VUserEntity vUserEntity = (VUserEntity)SessionContext.getSession(vOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        vOrderEntity.setAccountId(vUserEntity.getChildren().get(0).getAccountId());
        vOrderEntity.setOrderId(orderId);
        vOrderEntity.setCost(orderTotalPrice);
        vOrderEntity.setPostage(0);
        OrderEntity createOrderEntity = iOrderServices.create(vOrderEntity);
        if (createOrderEntity != null) {
            boolean attachToOrder = iCartServices.attachToOrder(createOrderEntity, vOrderEntity.getProductIds());
            if (attachToOrder) {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), new VOrderEntity(createOrderEntity), "success");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                vOrderEntity.setOrderId(null);
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        } else {
            vOrderEntity.setOrderId(null);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void createAnonymous() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        vOrderEntity.setStatus(1);
        /**
         * 保存匿名订单的收货人信息
         */
        VReceiverEntity receiverEntity = vOrderEntity.getReceiver();
        receiverEntity.setReceiverId(UUID.randomUUID().toString());
        ReceiverEntity resultReceiver = iReceiverService.create(null,receiverEntity);
        if (resultReceiver != null) {
            String orderId = UUID.randomUUID().toString();
            vOrderEntity.setOrderId(orderId);
            vOrderEntity.setReceiverId(resultReceiver.getReceiverId());
            /**
             * 保存匿名订单的订单信息
             */
            OrderEntity createOrderEntity = iOrderServices.create(vOrderEntity);
            if (createOrderEntity != null) {
                CartEntity cartEntity = new CartEntity();
                String mappingId = UUID.randomUUID().toString();
                cartEntity.setMappingId(mappingId);
                cartEntity.setFormatId(vOrderEntity.getProductIds()[0]);
                cartEntity.setAmount(1);
                cartEntity.setPricing(0);
                cartEntity.setOrderId(createOrderEntity.getOrderId());
                cartEntity.setStatus(2);
                CartEntity resultCartEntity = iCartServices.create(cartEntity);
                if (resultCartEntity != null) {
                    IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), new VOrderEntity(createOrderEntity), "success");
                    renderJson(JSON.toJSONString(iResultSet));
                } else {
                    IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "success");
                    renderJson(JSON.toJSONString(iResultSet));
                }
            } else {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Before(SessionInterceptor.class)
    @Override
    public void expressed() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        VUserEntity vUserEntity = (VUserEntity)SessionContext.getSession(vOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        OrderEntity result = iOrderServices.expressed(vUserEntity.getChildren(),vOrderEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vOrderEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vOrderEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Before(SessionInterceptor.class)
    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        VUserEntity vUserEntity = (VUserEntity)SessionContext.getSession(vOrderEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        LinkedList<VOrderEntity> result = new LinkedList<>();
        LinkedList<OrderEntity> orderEntities = iOrderServices.retrievesByAccounts(vUserEntity.getChildren(), 0, 0, 0);
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
            result.add(vOrderEntity1);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(result);
        iResultSet.setMessage("success");
        renderJson(JSON.toJSONString(iResultSet));
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
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mRetrievesByUser() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        LinkedList<VOrderEntity> result = new LinkedList<>();
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(vManagerEntity.getUser().getUserId());
        LinkedList<OrderEntity> orderEntities = iOrderServices.mRetrievesByUser(accountEntities, 0, 0, 0);
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
            result.add(vOrderEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(result);
        iResultSet.setMessage("success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
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
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(vOrderEntities);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mExpressing() {
        String params = this.getPara("p");
        VOrderEntity vOrderEntity = JSON.parseObject(params, VOrderEntity.class);
        OrderEntity result = iOrderServices.mExpressing(vOrderEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vOrderEntity);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            iResultSet.setData(vOrderEntity);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mQuery() {

    }
}
