package cn.foodslab.menu;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-07-30 14:41.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MenuServices implements IMenuServices {

    @Override
    public IResultSet retrieveMenusByLevel(int level) {
        List<Record> records = Db.find("SELECT * FROM menu ORDER BY queue");
        LinkedList<Map> resultMapList = new LinkedList<>();
        for (Record record : records) {
            resultMapList.add(record.getColumns());
        }
        IResultSet resultSet = new ResultSet(resultMapList);
        return resultSet;
    }

    @Override
    public IResultSet retrieveMenusByStatus(int status) {
        List<Record> records = Db.find("SELECT * FROM menu WHERE status = " + status + " ORDER BY queue");
        LinkedList<Map> resultMapList = new LinkedList<>();
        for (Record record : records) {
            resultMapList.add(record.getColumns());
        }
        IResultSet resultSet = new ResultSet(resultMapList);
        return resultSet;
    }

    @Override
    public IResultSet retrieveMenusByIds(String... ids) {
        return null;
    }
}
