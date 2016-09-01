package cn.foodslab.receiver;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverServices implements IReceiverService {

    @Override
    public IResultSet create(ReceiverEntity receiverEntity) {
        IResultSet resultSet = new ResultSet();
        Record record = new Record()
                .set("receiverId", receiverEntity.getReceiverId())
                .set("province", receiverEntity.getProvince())
                .set("city", receiverEntity.getCity())
                .set("county", receiverEntity.getCounty())
                .set("town", receiverEntity.getTown())
                .set("village", receiverEntity.getVillage())
                .set("append", receiverEntity.getAppend())
                .set("name", receiverEntity.getName())
                .set("phone0", receiverEntity.getPhone0())
                .set("phone1", receiverEntity.getPhone1())
                .set("status", receiverEntity.getStatus())
                .set("accountId", receiverEntity.getAccountId());
        boolean save = Db.save("user_receiver", record);
        if (save) {
            resultSet.setCode(200);
            resultSet.setData(receiverEntity);
            resultSet.setMessage("创建成功");
        } else {
            receiverEntity.setReceiverId(null);
            resultSet.setCode(500);
            resultSet.setData(receiverEntity);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet retrieve(String receiverId) {
        List<Record> records = Db.find("SELECT province,city,county,town,village,append,name,phone0,phone1 FROM user_receiver WHERE receiverId = ?", receiverId);
        Map<String, Object> receiverEntity = records.get(0).getColumns();
        IResultSet resultSet = new ResultSet(receiverEntity);
        resultSet.setCode(200);
        return resultSet;
    }
}
