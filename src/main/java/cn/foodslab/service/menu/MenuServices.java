package cn.foodslab.service.menu;

import cn.foodslab.service.manager.ManagerEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 14:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MenuServices implements IMenuServices {

    @Override
    public LinkedList<MenuEntity> retrievesByManager(ManagerEntity managerEntity) {
        LinkedList<MenuEntity> menuEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM menu WHERE menuId IN (SELECT menuId FROM manager_menu WHERE managerId = ? AND status != -1) AND status = 2 ORDER BY category, queue", managerEntity.getManagerId());
        for (Record record : records) {
            menuEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), MenuEntity.class));
        }
        return menuEntities;
    }

    @Override
    public LinkedList<MenuEntity> mRetrievesByAdmin() {
        LinkedList<MenuEntity> menuEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM menu ORDER BY category, queue");
        for (Record record : records) {
            menuEntities.add(JSON.parseObject(JSON.toJSONString(record.getColumns()), MenuEntity.class));
        }
        return menuEntities;
    }
}
