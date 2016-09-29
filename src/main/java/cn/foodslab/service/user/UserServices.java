package cn.foodslab.service.user;

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
        for (Record record:records){
            userEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), UserEntity.class));
        }
        return userEntities;
    }
}
