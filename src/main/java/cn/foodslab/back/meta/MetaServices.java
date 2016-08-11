package cn.foodslab.back.meta;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-11 14:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MetaServices {

    public IResultSet retrieveUnit(){
        List<Record> unitRecords = Db.find("SELECT * FROM meta_unit");
        List<Map> jsonMap = new LinkedList<>();
        for (Record record : unitRecords) {
            Map<String, Object> columns = record.getColumns();
            jsonMap.add(columns);
        }
        IResultSet resultSet = new ResultSet(jsonMap);
        return resultSet;
    }

}
