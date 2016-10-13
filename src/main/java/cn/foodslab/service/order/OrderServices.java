package cn.foodslab.service.order;

import cn.foodslab.service.cart.CartEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-08-31 14:21.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderServices implements IOrderServices {

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

    @Override
    public LinkedList<OrderEntity> retrievesByAccounts(String[] accountIds, int status, int pageIndex, int counter) {
        String in = "";
        for (String accountId : accountIds) {
            in = in + accountId + ",";
            if (in.length() > 0) {
                in = in.substring(0, in.length() - 1);
            }
        }
        List<Record> records;
        if (status == 0) {
            records = Db.find("SELECT * FROM user_order WHERE accountId IN (?)", in);
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND accountId IN (?)", status, in);
        }
        if (records == null) {
            return null;
        } else {
            LinkedList<OrderEntity> orderEntities = new LinkedList<>();
            for (Record record : records) {
                orderEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), OrderEntity.class));
            }
            return orderEntities;
        }
    }

    @Override
    public OrderEntity expressed(OrderEntity orderEntity) {
        int update = Db.update("UPDATE user_order SET status = 3 WHERE orderId = ? ", orderEntity.getOrderId());
        if (update == 1) {
            return orderEntity;
        }
        return null;
    }

    @Override
    public LinkedList<OrderEntity> query(String key, int pageIndex, int counter) {
        LinkedList<OrderEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_order WHERE orderId = ? ", key);
        OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), OrderEntity.class);
        result.add(orderEntity);
        return result;
    }

    @Override
    public LinkedList<OrderEntity> mRetrieves(int status, int pageIndex, int counter) {
        LinkedList<OrderEntity> orderEntities = new LinkedList<>();
        List<Record> records;
        if (status == 0) {
            records = Db.find("SELECT * FROM user_order WHERE status != -1 ");
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND status != -1 ", status);
        }
        if (records == null) {
            return null;
        } else {
            for (Record record : records) {
                OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(record.getColumns()), OrderEntity.class);
                orderEntities.add(orderEntity);
            }
            return orderEntities;
        }
    }

    @Override
    public LinkedList<OrderEntity> mRetrievesByUser(String[] accountIds, int status, int pageIndex, int counter) {
        String in = "";
        for (String accountId : accountIds) {
            in = in + accountId + ",";
            if (in.length() > 0) {
                in = in.substring(0, in.length() - 1);
            }
        }
        List<Record> records;
        if (status == 0) {
            records = Db.find("SELECT * FROM user_order WHERE accountId IN (?)", in);
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND accountId IN (?)", status, in);
        }
        if (records == null) {
            return null;
        } else {
            LinkedList<OrderEntity> orderEntities = new LinkedList<>();
            for (Record record : records) {
                orderEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), OrderEntity.class));
            }
            return orderEntities;
        }
    }

    @Override
    public OrderEntity mExpressing(OrderEntity orderEntity) {
        int update = Db.update("UPDATE user_order SET status = 2 ,expressLabel = ?, expressNumber = ? WHERE orderId = ? ",orderEntity.getExpressLabel(), orderEntity.getExpressNumber(), orderEntity.getOrderId());
        if (update == 1) {
            return orderEntity;
        }
        return null;
    }

    public LinkedList<OrderEntity> mRetrieveByStatus(int status) {
        LinkedList<OrderEntity> orderEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_order WHERE status = ? ", status);
        for (Record record : records) {
            OrderEntity orderEntity = JSON.parseObject(JSON.toJSONString(record.getColumns()), OrderEntity.class);
            orderEntities.add(orderEntity);
        }
        return orderEntities;
    }

    public OrderEntity updateExpress(OrderEntity orderEntity) {
        int update = Db.update("UPDATE user_order SET status = 2, expressLabel = ?,expressNumber = ? WHERE orderId = ? ", orderEntity.getExpressLabel(), orderEntity.getExpressNumber(), orderEntity.getOrderId());
        if (update == 1) {
            return orderEntity;
        }
        return null;
    }


    public LinkedList<CartEntity> retrievesByOrderId(String orderId) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE orderId = ? ", orderId);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    public CartEntity attachToOrder(CartEntity cartEntity) {
        int update = Db.update("UPDATE user_cart SET orderId = ?,status = ? WHERE mappingId = ?", cartEntity.getOrderId(), cartEntity.getStatus(), cartEntity.getMappingId());
        if (update == 1) {
            return cartEntity;
        } else {
            return null;
        }
    }
}
