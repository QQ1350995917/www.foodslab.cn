package cn.foodslab.service.order;

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

    @Override
    public LinkedList<OrderEntity> retrieveByAccount(String accountId) {
        LinkedList<OrderEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_order WHERE accountId = ? ", accountId);
        for (Record record:records){
            Map<String, Object> orderMap = record.getColumns();
            OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(orderMap), OrderEntity.class);
            result.add(orderEntity);
        }
        return result;
    }

    @Override
    public OrderEntity retrieveById(String orderId) {
        List<Record> records = Db.find("SELECT * FROM user_order WHERE orderId = ? ", orderId);
        OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), OrderEntity.class);
        return orderEntity;
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
