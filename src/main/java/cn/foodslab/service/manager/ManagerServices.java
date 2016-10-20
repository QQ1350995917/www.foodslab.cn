package cn.foodslab.service.manager;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 11:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 */
public class ManagerServices implements IManagerServices {

    @Override
    public ManagerEntity retrieve(ManagerEntity managerEntity) {
        return null;
    }

    @Override
    public ManagerEntity update(ManagerEntity managerEntity) {
        return null;
    }

    @Override
    public LinkedList<ManagerEntity> mRetrieves() {
        LinkedList<ManagerEntity> managerEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM manager WHERE status > 0 ORDER BY queue");
        for (Record record : records) {
            managerEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), ManagerEntity.class));
        }
        return managerEntities;
    }

    @Override
    public boolean exist(ManagerEntity managerEntity) {
        return false;
    }

    @Override
    public ManagerEntity mCreate(ManagerEntity managerEntity) {
        Db.update("UPDATE manager SET queue = queue + 1");
        Record record = new Record()
                .set("managerId", managerEntity.getManagerId())
                .set("loginName", managerEntity.getLoginName())
                .set("username", managerEntity.getUsername())
                .set("password", managerEntity.getPassword())
                .set("level", 1)
                .set("queue", managerEntity.getQueue())
                .set("status", managerEntity.getStatus());
        boolean result = Db.save("manager", "managerId", record);
        if (result) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mUpdate(ManagerEntity managerEntity) {
        Record record = new Record()
                .set("managerId", managerEntity.getManagerId())
                .set("loginName", managerEntity.getLoginName())
                .set("username", managerEntity.getUsername())
                .set("password", managerEntity.getPassword())
                .set("level", managerEntity.getLevel())
                .set("queue", managerEntity.getQueue())
                .set("status", managerEntity.getStatus());
        boolean result = Db.update("manager", "managerId", record);
        if (result) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mBlock(ManagerEntity managerEntity) {
        int update = Db.update("UPDATE manager SET status = 1 WHERE managerId = ? ", managerEntity.getManagerId());
        if (update == 1) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mUnBlock(ManagerEntity managerEntity) {
        int update = Db.update("UPDATE manager SET status = 2 WHERE managerId = ? ", managerEntity.getManagerId());
        if (update == 1) {
            return managerEntity;
        } else {
            return null;
        }
    }

    @Override
    public ManagerEntity mDelete(ManagerEntity managerEntity) {
        int update = Db.update("UPDATE manager SET status = -1 WHERE managerId = ? ", managerEntity.getManagerId());
        if (update == 1) {
            return managerEntity;
        } else {
            return null;
        }
    }
}
