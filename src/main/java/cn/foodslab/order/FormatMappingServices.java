package cn.foodslab.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
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
public class FormatMappingServices implements IFormatMappingServices {

    @Override
    public IResultSet create(List<FormatMappingEntity> formatMappingEntities) {

        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean result = true;
                for (FormatMappingEntity formatMappingEntity : formatMappingEntities){
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

        IResultSet resultSet = new ResultSet();

        if (succeed) {
            resultSet.setCode(200);
            resultSet.setData(formatMappingEntities);
            resultSet.setMessage("创建成功");
        } else {
            resultSet.setCode(500);
            resultSet.setData(formatMappingEntities);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }
}
