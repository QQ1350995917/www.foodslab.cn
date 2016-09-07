package cn.foodslab.cart;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
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
    public IResultSet retrieve() {
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_product WHERE status = 1");
        IResultSet resultSet = new ResultSet();
        LinkedList<Map<String,Object>> resultData = new LinkedList<>();
        for (Record record:records){
            Map<String, Object> cartEntity = record.getColumns();

            Record formatRecord = Db.find("SELECT * FROM product_format WHERE formatId = ?", cartEntity.get("formatId")).get(0);
            Map<String, Object> formatEntity = formatRecord.getColumns();

            Record typeRecord = Db.find("SELECT * FROM product_type WHERE typeId = ?", formatEntity.get("typeId")).get(0);
            Map<String, Object> typeEntity = typeRecord.getColumns();

            Record seriesRecord = Db.find("SELECT * FROM product_series WHERE seriesId = ?", typeEntity.get("seriesId")).get(0);
            Map<String, Object> seriesEntity = seriesRecord.getColumns();

            typeEntity.put("parent",seriesEntity);
            formatEntity.put("parent",typeEntity);
            cartEntity.put("product",formatEntity);

            resultData.add(cartEntity);
        }
        resultSet.setCode(200);
        resultSet.setData(resultData);
        return resultSet;
    }

    @Override
    public IResultSet create(CartEntity cartEntity) {
        IResultSet resultSet = new ResultSet();
        Record record = new Record()
                .set("mappingId", cartEntity.getMappingId())
                .set("formatId", cartEntity.getFormatId())
                .set("amount", cartEntity.getAmount())
                .set("accountId", cartEntity.getAccountId());
        boolean save = Db.save("user_product", record);
        if (save) {
            resultSet.setCode(200);
            resultSet.setData(cartEntity);
            resultSet.setMessage("创建成功");
        } else {
            cartEntity.setMappingId(null);
            resultSet.setCode(500);
            resultSet.setData(cartEntity);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet update(CartEntity cartEntity) {
        IResultSet resultSet = new ResultSet();
        int update = Db.update("UPDATE user_product SET amount = ? WHERE mappingId = ?", cartEntity.getAmount(), cartEntity.getMappingId());
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setData(cartEntity);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setData(cartEntity);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet delete(List<CartEntity> cartEntities) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (CartEntity cartEntity : cartEntities) {
                    int count = Db.update("DELETE FROM user_product WHERE mappingId = ?", cartEntity.getMappingId());
                    result = count == 1 && result;
                }
                return result;
            }
        });

        IResultSet resultSet = new ResultSet();
        if (succeed) {
            resultSet.setCode(200);
            resultSet.setData(cartEntities);
            resultSet.setMessage("删除成功");
        } else {
            resultSet.setCode(500);
            resultSet.setData(cartEntities);
            resultSet.setMessage("删除失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet isExist(CartEntity cartEntity) {
        IResultSet resultSet = new ResultSet();
        List<Record> records = Db.find("SELECT mappingId,formatId,amount,accountId,createTime FROM user_product WHERE status = 1 AND accountId = ? AND formatId = ?", cartEntity.getAccountId(), cartEntity.getFormatId());
        if (records == null || records.size() == 0) {
            resultSet.setCode(200);
            resultSet.setData(false);
        } else {
            resultSet.setCode(300);
            resultSet.setData(records.get(0).getColumns());
        }
        return resultSet;
    }
}
