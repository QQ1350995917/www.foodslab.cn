package cn.foodslab.order;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-08-31 17:24.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderProductMappingServices implements IOrderProductMappingServices {

    @Override
    public List<OrderProductMappingEntity> create(List<OrderProductMappingEntity> formatMappingEntities) {

        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (OrderProductMappingEntity formatMappingEntity : formatMappingEntities){
                    Record record = new Record()
                            .set("mappingId", formatMappingEntity.getMappingId())
                            .set("orderId", formatMappingEntity.getOrderId())
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
