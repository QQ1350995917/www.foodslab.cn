package cn.foodslab.controller.cart;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.controller.product.VSeriesEntity;
import cn.foodslab.controller.product.VTypeEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.product.*;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartController extends Controller implements ICartController {

    private ICartServices iCartServices = new CartServices();
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();
    private IAccountServices iAccountServices = new AccountServices();

    @Override
    public void index() {

    }

    @Override
    @Before(SessionInterceptor.class)
    public void create() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        vCartEntity.setMappingId(UUID.randomUUID().toString());
        HttpSession session = SessionContext.getSession(vCartEntity.getCs());
        VUserEntity vUserEntity = (VUserEntity)session.getAttribute(SessionContext.KEY_USER);
        vCartEntity.setAccountId(vUserEntity.getChildren().get(0).getAccountId());
        CartEntity cartEntityInCart = iCartServices.exist(vCartEntity);
        if (cartEntityInCart == null) {
            CartEntity createResult = iCartServices.create(vCartEntity);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), createResult, "success");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            int amount = vCartEntity.getAmount() + cartEntityInCart.getAmount();
            vCartEntity.setMappingId(cartEntityInCart.getMappingId());
            vCartEntity.setAmount(amount);
            CartEntity updateResult = iCartServices.updateAmount(vCartEntity);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), updateResult, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void update() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(SessionContext.getSession(vCartEntity.getCs()).toString());
        vCartEntity.setAccountId(accountEntities.get(0).getAccountId());
        CartEntity updateResult = iCartServices.updateAmount(vCartEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), updateResult, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before(SessionInterceptor.class)
    public void retrieves() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        LinkedList<CartEntity> cartEntities;
        VUserEntity vUserEntity = (VUserEntity)SessionContext.getSession(vCartEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        if (vCartEntity.getMappingIds() != null) {
            cartEntities = iCartServices.retrievesByIds(vUserEntity.getChildren(),vCartEntity.getMappingIds());
        } else {
            cartEntities = iCartServices.retrievesByAccounts(vUserEntity.getChildren());
        }
        if (cartEntities == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vCartEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<VCartEntity> vCartEntities = new LinkedList<>();
            for (CartEntity cartEntity : cartEntities) {
                FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
                TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
                SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
                vTypeEntity.setParent(vSeriesEntity);
                vFormatEntity.setParent(vTypeEntity);
                VCartEntity responseVCartEntity = new VCartEntity(cartEntity);
                responseVCartEntity.setFormatEntity(vFormatEntity);
                vCartEntities.add(responseVCartEntity);
            }
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vCartEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before(SessionInterceptor.class)
    public void delete() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        VUserEntity vUserEntity = (VUserEntity)SessionContext.getSession(vCartEntity.getCs()).getAttribute(SessionContext.KEY_USER);
        CartEntity delete = iCartServices.deleteById(vUserEntity.getChildren(),vCartEntity);
        if (delete == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vCartEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), delete, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    @Override
    public void mRetrieve() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        LinkedList<AccountEntity> accountEntities = iAccountServices.mRetrieveByUserId(vUserEntity.getUserId());
        LinkedList<CartEntity> cartEntities = iCartServices.retrievesByAccounts(accountEntities);
        LinkedList<VCartEntity> result = new LinkedList<>();
        for (CartEntity cartEntity : cartEntities) {
            FormatEntity formatEntity = iFormatServices.retrieveById(cartEntity.getFormatId());
            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
            vTypeEntity.setParent(vSeriesEntity);
            vFormatEntity.setParent(vTypeEntity);
            VCartEntity responseVCartEntity = new VCartEntity(cartEntity);
            responseVCartEntity.setFormatEntity(vFormatEntity);
            result.add(responseVCartEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }
}
