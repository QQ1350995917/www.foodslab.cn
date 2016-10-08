package cn.foodslab.service.user;

import cn.foodslab.service.product.FormatEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-09-29 18:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserServices implements IUserServices {

    @Override
    public LinkedList<UserEntity> mRetrieve() {
        LinkedList<UserEntity> userEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM user WHERE status != -1");
        for (Record record : records) {
            userEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), UserEntity.class));
        }
        return userEntities;
    }

    @Override
    public UserEntity mRetrieveById(String userId) {
        List<Record> records = Db.find("SELECT * FROM user WHERE status != -1 AND userId = ? order by createTime ASC ", userId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), UserEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public UserEntity mBlock(UserEntity userEntity) {
        int count = Db.update("UPDATE user SET status = 0 WHERE userId = ? ", userEntity.getUserId());
        if (count == 1) {
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public UserEntity mUnBlock(UserEntity userEntity) {
        int count = Db.update("UPDATE user SET status = 1 WHERE userId = ? ", userEntity.getUserId());
        if (count == 1) {
            return userEntity;
        } else {
            return null;
        }
    }
}
