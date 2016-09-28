package cn.foodslab.controller.cart;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.model.cart.VCartEntity;
import cn.foodslab.model.product.VFormatEntity;
import cn.foodslab.model.product.VSeriesEntity;
import cn.foodslab.model.product.VTypeEntity;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.List;
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

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        LinkedList<CartEntity> cartEntities;
        if (vCartEntity.getMappingIds() != null) {
            String[] split = vCartEntity.getMappingIds().split(",");
            cartEntities = iCartServices.retrieveByIds(split);
        } else {
            cartEntities = iCartServices.retrieveByAccountId(vCartEntity.getSessionId());
        }
        if (cartEntities == null) {
            IResultSet iResultSet = new ResultSet(3000, vCartEntity, "fail");
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
            IResultSet iResultSet = new ResultSet(3050, vCartEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        CartEntity cartEntity = vCartEntity.getCartEntity();
        cartEntity.setMappingId(UUID.randomUUID().toString());
        cartEntity.setAccountId(vCartEntity.getSessionId());
        CartEntity cartEntityInCart = iCartServices.isExistInCart(cartEntity);
        if (cartEntityInCart == null) {
            CartEntity createResult = iCartServices.create(cartEntity);
            IResultSet iResultSet = new ResultSet(3050, createResult, "success");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            int amount = vCartEntity.getAmount() + cartEntityInCart.getAmount();
            cartEntity.setMappingId(cartEntityInCart.getMappingId());
            cartEntity.setAmount(amount);
            CartEntity updateResult = iCartServices.updateAmount(cartEntity);
            IResultSet iResultSet = new ResultSet(3050, updateResult, "success");
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
        IResultSet iResultSet = new ResultSet(3050, updateResult, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void delete() {
        String params = this.getPara("p");
        VCartEntity vCartEntity = JSON.parseObject(params, VCartEntity.class);
        CartEntity cartEntity = vCartEntity.getCartEntity();
        List<CartEntity> delete = iCartServices.deleteByIds(cartEntity.getMappingId());
        if (delete == null) {
            IResultSet iResultSet = new ResultSet(3000, vCartEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, delete, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }
}
