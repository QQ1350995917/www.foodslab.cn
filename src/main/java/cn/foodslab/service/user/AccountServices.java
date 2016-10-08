package cn.foodslab.service.user;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-16 10:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountServices implements IAccountServices {

    @Override
    public AccountEntity retrieve(String phoneNumber) {
        List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE identity = ? ", phoneNumber);
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        AccountEntity accountEntity = JSON.parseObject(JSON.toJSONString(accountMap), AccountEntity.class);
        return accountEntity;
    }

    @Override
    public LinkedList<AccountEntity> retrieveAccountsByUserId(String userId) {
        LinkedList<AccountEntity> result = new LinkedList<>();
        List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE userId = ? ", userId);
        for (Record accountRecord : accountRecords) {
            Map<String, Object> accountMap = accountRecord.getColumns();
            AccountEntity accountEntity = JSON.parseObject(JSON.toJSONString(accountMap), AccountEntity.class);
            result.add(accountEntity);
        }
        return result;
    }

    @Override
    public UserEntity retrieveUserByAccountId(String account) {
        List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE accountId = ? ", account);
        Map<String, Object> accountMap = accountRecords.get(0).getColumns();
        List<Record> userRecords = Db.find("SELECT * FROM user WHERE userId = ? ", accountMap.get("userId").toString());
        Map<String, Object> userMap = userRecords.get(0).getColumns();
        UserEntity userEntity = JSON.parseObject(JSON.toJSONString(userMap), UserEntity.class);
        return userEntity;
    }

    @Override
    public AccountEntity create(AccountEntity accountEntity) {
        Record userRecord = new Record()
                .set("userId", accountEntity.getUserId())
                .set("status", 1);
        Record userAccountRecord = new Record()
                .set("accountId", accountEntity.getAccountId())
                .set("identity", accountEntity.getIdentity())
                .set("userId", accountEntity.getUserId());
        boolean user = Db.save("user", userRecord);
        boolean account = Db.save("user_account", userAccountRecord);
        if (user && account) {
            return accountEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<AccountEntity> bind(AccountEntity accountEntity1, AccountEntity accountEntity2) {
        return null;
    }

    @Override
    public AccountEntity update(AccountEntity accountEntity) {
        return null;
    }
}
