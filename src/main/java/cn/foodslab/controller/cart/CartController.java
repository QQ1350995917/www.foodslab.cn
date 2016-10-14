package cn.foodslab.controller.cart;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.controller.product.VSeriesEntity;
import cn.foodslab.controller.product.VTypeEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.product.*;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

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
    public void create() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        CartEntity cartEntity = vCartEntity.getCartEntity();
        cartEntity.setMappingId(UUID.randomUUID().toString());
        cartEntity.setAccountId(vCartEntity.getSessionId());
        CartEntity cartEntityInCart = iCartServices.exist(cartEntity);
        if (cartEntityInCart == null) {
            CartEntity createResult = iCartServices.create(cartEntity);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), createResult, "success");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            int amount = vCartEntity.getAmount() + cartEntityInCart.getAmount();
            cartEntity.setMappingId(cartEntityInCart.getMappingId());
            cartEntity.setAmount(amount);
            CartEntity updateResult = iCartServices.updateAmount(cartEntity);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), updateResult, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void update() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        CartEntity cartEntity = vCartEntity.getCartEntity();
        cartEntity.setAccountId(vCartEntity.getSessionId());
        CartEntity updateResult = iCartServices.updateAmount(cartEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), updateResult, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }


    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        LinkedList<CartEntity> cartEntities;
        if (vCartEntity.getMappingIds() != null) {
            String[] accounts = new String[]{};
            String[] split = vCartEntity.getMappingIds().split(",");
            cartEntities = iCartServices.retrievesByIds(accounts,split);
        } else {
            cartEntities = iCartServices.retrievesByAccountId(vCartEntity.getSessionId());
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
    public void delete() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        CartEntity cartEntity = vCartEntity.getCartEntity();
        CartEntity delete = iCartServices.deleteById(cartEntity);
        if (delete == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vCartEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), delete, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mRetrieve() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(vUserEntity.getUserId());

        String[] accountIds = new String[accountEntities.size()];
        for (int index = 0; index < accountEntities.size(); index++) {
            accountIds[index] = accountEntities.get(index).getAccountId();
        }
        LinkedList<CartEntity> cartEntities = iCartServices.retrievesByAccountIds(accountIds);
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
