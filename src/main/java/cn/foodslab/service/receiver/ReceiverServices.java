package cn.foodslab.service.receiver;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverServices implements IReceiverService {

    @Override
    public LinkedList<ReceiverEntity> retrieves(String accountId) {
        return this.retrieves(new String[]{accountId});
    }

    @Override
    public LinkedList<ReceiverEntity> retrieves(String[] accountIds) {
        String in = "";
        for (String accountId : accountIds) {
            in = in + " ? ,";
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        LinkedList<ReceiverEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_receiver WHERE accountId IN (" + in + ") AND status > 1 ORDER BY createTime ASC", accountIds);
        for (Record record : records) {
            Map<String, Object> receiverMap = record.getColumns();
            ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverMap), ReceiverEntity.class);
            result.add(receiverEntity);
        }
        return result;
    }

    @Override
    public ReceiverEntity retrieveById(String receiverId) {
        List<Record> receiverRecords = Db.find("SELECT * FROM user_receiver WHERE receiverId = ? ", receiverId);
        ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverRecords.get(0).getColumns()), ReceiverEntity.class);
        return receiverEntity;
    }

    @Override
    public ReceiverEntity create(ReceiverEntity receiverEntity) {
        Record record = new Record()
                .set("receiverId", receiverEntity.getReceiverId())
                .set("name", receiverEntity.getName())
                .set("phone0", receiverEntity.getPhone0())
                .set("phone1", receiverEntity.getPhone1())
                .set("province", receiverEntity.getProvince())
                .set("city", receiverEntity.getCity())
                .set("county", receiverEntity.getCounty())
                .set("town", receiverEntity.getTown())
                .set("village", receiverEntity.getVillage())
                .set("append", receiverEntity.getAppend())
                .set("status", receiverEntity.getStatus())
                .set("accountId", receiverEntity.getAccountId());
        boolean save = Db.save("user_receiver", record);
        if (save) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public ReceiverEntity updateById(ReceiverEntity receiverEntity) {
        Record record = new Record()
                .set("receiverId", receiverEntity.getReceiverId())
                .set("name", receiverEntity.getName())
                .set("phone0", receiverEntity.getPhone0())
                .set("phone1", receiverEntity.getPhone1())
                .set("province", receiverEntity.getProvince())
                .set("city", receiverEntity.getCity())
                .set("county", receiverEntity.getCounty())
                .set("town", receiverEntity.getTown())
                .set("village", receiverEntity.getVillage())
                .set("append", receiverEntity.getAppend());
        boolean update = Db.update("user_receiver", "receiverId", record);
        if (update) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public ReceiverEntity deleteById(String receiverId) {
        ReceiverEntity receiverEntity = this.retrieveById(receiverId);
        int update = Db.update("UPDATE user_receiver SET status = -1 WHERE receiverId = ? ", receiverId);
        return update == 1 ? receiverEntity : null;
    }


    @Override
    public ReceiverEntity kingReceiverInUser(ReceiverEntity receiverEntity, String[] accountIds) {
        String in = "";
        for (String accountId : accountIds) {
            in = in + " ? ,";
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        Db.update("UPDATE user_receiver SET status = 2 WHERE accountId IN (" + in + ") AND status != -1", accountIds);
        int update = Db.update("UPDATE user_receiver SET status = 3 WHERE receiverId = ? ", receiverEntity.getReceiverId());
        if (update == 1) {
            return receiverEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<ReceiverEntity> mRetrieves(String accountId) {
        return this.mRetrieves(new String[]{accountId});
    }

    @Override
    public LinkedList<ReceiverEntity> mRetrieves(String[] accountIds) {
        String in = "";
        for (String accountId : accountIds) {
            in = in + " ? ,";
        }
        if (in.length() > 0) {
            in = in.substring(0, in.length() - 1);
        }
        LinkedList<ReceiverEntity> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user_receiver WHERE accountId IN (" + in + ") AND status > 1", accountIds);
        for (Record record : records) {
            Map<String, Object> receiverMap = record.getColumns();
            ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverMap), ReceiverEntity.class);
            result.add(receiverEntity);
        }
        return result;
    }
}
