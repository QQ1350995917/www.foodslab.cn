package cn.foodslab.service.order;

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
        String[] params = new String[accountIds.length + 1];
        int index = 0;
        params[index] = status + "";
        index ++;
        String in = "";
        for (String accountId : accountIds) {
            in = in + " ? ,";
            params[index] = accountId;
            index ++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        List<Record> records;
        if (status == 0) {
            records = Db.find("SELECT * FROM user_order WHERE accountId IN (" + in + ") ORDER BY createTime DESC", accountIds);
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND accountId IN (" + in + ") ORDER BY createTime DESC", params);
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
            records = Db.find("SELECT * FROM user_order WHERE status != -1 ORDER BY createTime DESC");
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND status != -1 ORDER BY createTime ASC", status);
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
        String[] params = new String[accountIds.length + 1];
        int index = 0;
        params[index] = status + "";
        index ++;
        String in = "";
        for (String accountId : accountIds) {
            in = in + " ? ,";
            params[index] = accountId;
            index ++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        List<Record> records;
        if (status == 0) {
            records = Db.find("SELECT * FROM user_order WHERE accountId IN (" + in + ") ORDER BY createTime DESC", accountIds);
        } else {
            records = Db.find("SELECT * FROM user_order WHERE status = ? AND accountId IN (" + in + ") ORDER BY createTime DESC", params);
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
}
