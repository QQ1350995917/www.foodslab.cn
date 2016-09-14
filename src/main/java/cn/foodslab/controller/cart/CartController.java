package cn.foodslab.controller.cart;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartController extends Controller implements ICartController {

    private ICartServices iCartServices = new CartServices();

    @Override
    public void retrieve() {
        IResultSet resultSet = new ResultSet();
        LinkedList<Map<String, Object>> result = iCartServices.retrieve();
        resultSet.setCode(200);
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        IResultSet resultSet = new ResultSet();
        String accountId = this.getPara("accountId");
        String formatId = this.getPara("formatId");
        int amount = this.getParaToInt("amount");
        CartEntity cartEntity = new CartEntity(formatId, amount, accountId);
        cartEntity.setMappingId(UUID.randomUUID().toString());
        CartEntity cartEntityResult = iCartServices.isExist(cartEntity);
        if (cartEntityResult != null) {
            amount = amount + cartEntityResult.getAmount();
            cartEntity.setMappingId(cartEntityResult.getMappingId());
            cartEntity.setAmount(amount);
            CartEntity updateResult = iCartServices.update(cartEntity);
            resultSet.setCode(200);
            resultSet.setData(updateResult);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            CartEntity createResult = iCartServices.create(cartEntity);
            resultSet.setCode(200);
            renderJson(JSON.toJSONString(createResult));
        }
    }

    @Override
    public void update() {
        IResultSet resultSet = new ResultSet();
        String accountId = this.getPara("accountId");
        String mapping = this.getPara("mapping");
        int amount = this.getParaToInt("amount");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMappingId(mapping);
        cartEntity.setAmount(amount);
        cartEntity.setAccountId(accountId);
        CartEntity updateResult = iCartServices.update(cartEntity);
        resultSet.setCode(200);
        resultSet.setData(updateResult);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void delete() {
        IResultSet resultSet = new ResultSet();
        String accountId = this.getPara("accountId");
        String mapping = this.getPara("mapping");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMappingId(mapping);
        cartEntity.setAccountId(accountId);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        cartEntities.add(cartEntity);
        List<CartEntity> delete = iCartServices.delete(cartEntities);
        resultSet.setCode(200);
        resultSet.setData(delete);
        renderJson(JSON.toJSONString(resultSet));
    }
}
