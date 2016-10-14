package cn.foodslab.service.cart;

import cn.foodslab.service.order.OrderEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartServices implements ICartServices {

    @Override
    public CartEntity exist(CartEntity cartEntity) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND accountId = ? AND formatId = ?", cartEntity.getAccountId(), cartEntity.getFormatId());
        if (records == null || records.size() != 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public CartEntity create(CartEntity cartEntity) {
        Record record = new Record()
                .set("mappingId", cartEntity.getMappingId())
                .set("formatId", cartEntity.getFormatId())
                .set("amount", cartEntity.getAmount())
                .set("accountId", cartEntity.getAccountId());
        boolean save = Db.save("user_cart", record);
        if (save) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public CartEntity updateAmount(CartEntity cartEntity) {
        int update = Db.update("UPDATE user_cart SET amount = ? WHERE mappingId = ?", cartEntity.getAmount(), cartEntity.getMappingId());
        if (update == 1) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public CartEntity deleteById(CartEntity cartEntity) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE mappingId = ?", cartEntity.getMappingId());
        if (records.size() > 0) {
            Record record = records.get(0);
            boolean delete = Db.delete("user_cart", "mappingId", record);
            return JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class);
        } else {
            return null;
        }

    }

    @Override
    public LinkedList<CartEntity> retrievesByAccountId(String accountId) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND accountId = ?", accountId);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public LinkedList<CartEntity> retrievesByAccountIds(String[] accountIds) {
        return null;
    }

    @Override
    public CartEntity retrieveById(String accountId, String mappingId) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND mappingId = ?", mappingId);
        return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
    }

    @Override
    public LinkedList<CartEntity> retrievesByIds(String[] accountIds, String[] mappingIds) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (String mappingId : mappingIds) {
            List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND mappingId = ?", mappingId);
            cartEntities.add(JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public LinkedList<CartEntity> retrievesByOrderId(String[] accountIds, String orderId) {
        String[] params = new String[accountIds.length + 1];
        int index = 0;
        params[index] = orderId;
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

        List<Record> records = Db.find("SELECT * FROM user_cart WHERE orderId = ? AND accountId IN (" + in + ")", params);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public boolean attachToOrder(OrderEntity orderEntity, String[] mappingIds) {
        String[] params = new String[mappingIds.length + 1];
        int index = 0;
        params[index] = orderEntity.getOrderId();
        index ++;
        String in = "";
        for (String mappingId : mappingIds) {
            in = in + " ? ,";
            params[index] = mappingId;
            index ++;
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }

        int update = Db.update("UPDATE user_cart SET orderId = ? ,status = 2 WHERE mappingId IN (" + in + ") AND status = 1", params);
        if (update == mappingIds.length) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public LinkedList<CartEntity> mRetrievesByOrderId(String[] accountIds, String orderId) {
        return this.retrievesByOrderId(accountIds, orderId);
    }

    @Override
    public LinkedList<CartEntity> mRetrievesByOrderId(String orderId) {
        List<Record> records = Db.find("SELECT * FROM user_cart WHERE orderId = ? ", orderId);
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }
}
