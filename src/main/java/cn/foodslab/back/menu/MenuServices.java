package cn.foodslab.back.menu;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 14:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MenuServices implements IMenuServices {

    @Override
    public IResultSet retrieveMenusByLevel(int level) {
        List<Record> records = Db.find("SELECT * FROM menu ORDER BY queue");
        IResultSet resultSet = new ResultSet(JSON.toJSONString(records));
        return resultSet;
    }

    @Override
    public IResultSet retrieveMenusByIds(String... ids) {
        return null;
    }
}
