package cn.foodslab.service.order;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 17:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Order2ProductServices implements IOrder2ProductServices {

    @Override
    public LinkedList<Order2ProductEntity> retrieve(String mapping) {
        LinkedList<Order2ProductEntity> result = new LinkedList<>();
        List<Record> mappingRecords = Db.find("SELECT * FROM user_order_product WHERE mappingId = ?", mapping);
        for (Record mappingRecord:mappingRecords){
            Map<String, Object> mappingMap = mappingRecord.getColumns();
            Order2ProductEntity order2ProductEntity = JSON.parseObject(JSON.toJSONString(mappingMap), Order2ProductEntity.class);
            result.add(order2ProductEntity);
        }
        return result;
    }

    @Override
    public LinkedList<Order2ProductEntity> create(LinkedList<Order2ProductEntity> formatMappingEntities) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (Order2ProductEntity formatMappingEntity : formatMappingEntities){
                    Record record = new Record()
                            .set("mappingId", formatMappingEntity.getMappingId())
                            .set("orderId", formatMappingEntity.getOrderId())
                            .set("amount", formatMappingEntity.getAmount())
                            .set("totalPrice", formatMappingEntity.getTotalPrice())
                            .set("formatId", formatMappingEntity.getFormatId());
                    result = result && Db.save("user_order_product", record);
                    if (!result){
                        break;
                    }
                }
                return result;
            }
        });

        if (succeed) {
            return formatMappingEntities;
        } else {
            return null;
        }
    }
}
