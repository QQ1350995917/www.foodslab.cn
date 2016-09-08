package cn.foodslab.cart;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-05 11:27.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartServices implements ICartServices {

    @Override
    public LinkedList<Map<String,Object>> retrieve() {
        LinkedList<Map<String,Object>> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1");

        for (Record record:records){
            Map<String, Object> cartMap = record.getColumns();

            Record formatRecord = Db.find("SELECT * FROM product_format WHERE formatId = ?", cartMap.get("formatId")).get(0);
            Map<String, Object> formatMap = formatRecord.getColumns();

            Record typeRecord = Db.find("SELECT * FROM product_type WHERE typeId = ?", formatMap.get("typeId")).get(0);
            Map<String, Object> typeMap = typeRecord.getColumns();

            Record seriesRecord = Db.find("SELECT * FROM product_series WHERE seriesId = ?", typeMap.get("seriesId")).get(0);
            Map<String, Object> seriesMap = seriesRecord.getColumns();

            typeMap.put("parent", seriesMap);
            formatMap.put("parent", typeMap);
            cartMap.put("product", formatMap);

            result.add(cartMap);
        }
        return result;
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
    public CartEntity update(CartEntity cartEntity) {
        int update = Db.update("UPDATE user_cart SET amount = ? WHERE mappingId = ?", cartEntity.getAmount(), cartEntity.getMappingId());
        if (update == 1) {
            return cartEntity;
        } else {
            return null;
        }
    }

    @Override
    public List<CartEntity> delete(List<CartEntity> cartEntities) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (CartEntity cartEntity : cartEntities) {
                    int count = Db.update("DELETE FROM user_cart WHERE mappingId = ?", cartEntity.getMappingId());
                    result = count == 1 && result;
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
    public CartEntity isExist(CartEntity cartEntity) {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_cart WHERE status = 1 AND accountId = ? AND formatId = ?", cartEntity.getAccountId(), cartEntity.getFormatId());
        if (records == null || records.size() == 0) {
            return cartEntity;
        } else {
            return null;
        }
    }
}
