package cn.foodslab.service.receiver;

import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.service.user.UserEntity;
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
    private IAccountServices iAccountServices = new AccountServices();

    @Override
    public LinkedList<ReceiverEntity> retrieve(UserEntity userEntity) {
        LinkedList<ReceiverEntity> result = new LinkedList<>();
        LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveAccountsByUserId(userEntity.getUserId());
        for (AccountEntity accountEntity:accountEntities){
            List<Record> receiverRecords = Db.find("SELECT * FROM user_receiver WHERE accountId = ? ", accountEntity.getAccountId());
            for (Record receiverRecord:receiverRecords){
                Map<String, Object> receiverMap = receiverRecord.getColumns();
                ReceiverEntity receiverEntity = JSON.parseObject(JSON.toJSONString(receiverMap), ReceiverEntity.class);
                result.add(receiverEntity);
            }
        }
        return result;
    }

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
