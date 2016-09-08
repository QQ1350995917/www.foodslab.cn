package cn.foodslab.receiver;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverServices implements IReceiverService {

    @Override
    public ReceiverEntity create(ReceiverEntity receiverEntity) {
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
            return receiverEntity;
        } else {
            return null;
        }
    }

}
