package cn.foodslab.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 14:21.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderServices implements IOrderServices {

    @Override
    public IResultSet create(OrderEntity orderEntity) {
        IResultSet resultSet = new ResultSet();
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
            resultSet.setCode(200);
            resultSet.setData(orderEntity);
            resultSet.setMessage("创建成功");
        } else {
            orderEntity.setOrderId(null);
            resultSet.setCode(500);
            resultSet.setData(orderEntity);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }


    @Override
    public IResultSet retrieve(String orderId) {
        List<Record> records = Db.find("SELECT orderId,senderName,senderPhone,receiverId,expressLabel,expressNumber,createTime FROM user_order WHERE orderId = ?", orderId);
        Map<String, Object> receiverEntity = records.get(0).getColumns();
        IResultSet resultSet = new ResultSet(receiverEntity);
        resultSet.setCode(200);
        return resultSet;
    }
}
