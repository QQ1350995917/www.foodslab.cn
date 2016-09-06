package cn.foodslab.cart;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
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
        IResultSet retrieve = iCartServices.retrieve();
        renderJson(JSON.toJSONString(retrieve));
    }

    @Override
    public void create() {
        String accountId = this.getPara("accountId");
        String formatId = this.getPara("formatId");
        int amount = this.getParaToInt("amount");
        CartEntity cartEntity = new CartEntity(formatId, amount, accountId);
        cartEntity.setMappingId(UUID.randomUUID().toString());
        IResultSet exist = iCartServices.isExist(cartEntity);
        if (exist.getCode() == 300) {
            Map<String, Object> data = (Map<String, Object>) exist.getData();
            int existAmount = (int)data.get("amount");
            amount = amount + existAmount;
            cartEntity.setMappingId(data.get("mappingId").toString());
            cartEntity.setAmount(amount);
            IResultSet resultSet = iCartServices.update(cartEntity);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            IResultSet resultSet = iCartServices.create(cartEntity);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void update() {
        String accountId = this.getPara("accountId");
        String mapping = this.getPara("mapping");
        int amount = this.getParaToInt("amount");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMappingId(mapping);
        cartEntity.setAmount(amount);
        cartEntity.setAccountId(accountId);
        IResultSet resultSet = iCartServices.update(cartEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void delete() {
        String accountId = this.getPara("accountId");
        String mapping = this.getPara("mapping");
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMappingId(mapping);
        cartEntity.setAccountId(accountId);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        cartEntities.add(cartEntity);
        IResultSet resultSet = iCartServices.delete(cartEntities);
        renderJson(JSON.toJSONString(resultSet));
    }
}
