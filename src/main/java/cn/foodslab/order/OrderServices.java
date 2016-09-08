package cn.foodslab.order;

import cn.foodslab.user.AccountEntity;
import cn.foodslab.user.AccountServices;
import cn.foodslab.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 14:21.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderServices implements IOrderServices {
    IAccountServices accountServices = new AccountServices();

    @Override
    public LinkedList<OrderEntity> retrieve(String userId) {
        LinkedList<OrderEntity> result = new LinkedList<>();
        LinkedList<AccountEntity> accountEntities = accountServices.retrieveAccountsByUserId(userId);
        for (AccountEntity accountEntity:accountEntities){
            List<Record> records = Db.find("SELECT * FROM user_order WHERE accountId = ? ", accountEntity.getAccountId());
            for (Record record:records){
                Map<String, Object> orderMap = record.getColumns();
                OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(orderMap), OrderEntity.class);
                result.add(orderEntity);
            }
        }
        return result;
    }

    @Override
    public OrderEntity create(OrderEntity orderEntity) {
        Record record = new Record()
                .set("orderId", orderEntity.getOrderId())
                .set("accountId", orderEntity.getAccountId())
                .set("senderName", orderEntity.getSenderName())
                .set("senderPhone", orderEntity.getSenderPhone())
                .set("receiverId", orderEntity.getReceiverId())
                .set("cost", orderEntity.getCost())
                .set("postage", orderEntity.getPostage())
                .set("status", orderEntity.getStatus());
        boolean save = Db.save("user_order", record);
        if (save) {
            return orderEntity;
        } else {
            return null;
        }
    }
}
