package cn.foodslab.service.cart;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartServices implements ICartServices {
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
    public CartEntity retrieveById(String mappingId) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND mappingId = ?", mappingId);
        return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
    }

    @Override
    public CartEntity retrieveCartByFormatId(String formatId) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND formatId = ?", formatId);
        return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
    }

    @Override
    public LinkedList<CartEntity> retrieveByOrderId(String orderId) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE orderId = ? ", orderId);
        for (Record record:records){
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public CartEntity attachToOrder(CartEntity cartEntity) {
        int update = Db.update("UPDATE user_cart SET orderId = ?,status = ? WHERE mappingId = ?", cartEntity.getOrderId(), cartEntity.getStatus(), cartEntity.getMappingId());
        if (update == 1) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<CartEntity> retrieveByIds(String... mappingIds) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        for (String mappingId : mappingIds) {
            List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND mappingId = ?", mappingId);
            cartEntities.add(JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class));
        }
        return cartEntities;
    }

    @Override
    public LinkedList<CartEntity> retrieveByAccountId(String accountId) {
        LinkedList<CartEntity> cartEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND accountId = ?", accountId);
        for (Record record : records) {
            cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
        }
        return cartEntities;
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
    public List<CartEntity> deleteByIds(String... mappingIds) {
        List<CartEntity> cartEntities = new LinkedList<>();
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (String mappingId : mappingIds) {
                    List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE mappingId = ?", mappingId);
                    Record record = records.get(0);
                    boolean delete = Db.delete("user_cart", "mappingId", record);
                    result = delete && result;
                    cartEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), CartEntity.class));
                }
                return result;
            }
        });

        if (succeed) {
            return cartEntities;
        } else {
            return null;
        }
    }

    @Override
    public CartEntity isExistInCart(CartEntity cartEntity) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND accountId = ? AND formatId = ?", cartEntity.getAccountId(), cartEntity.getFormatId());
        if (records == null || records.size() != 0) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), CartEntity.class);
        } else {
            return null;
        }
    }
}
