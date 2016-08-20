package cn.foodslab.common.meta;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
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
public class MetaServices implements IMetaServices {

    @Override
    public IResultSet retrieveUnit() {
        List<Record> unitRecords = Db.find("SELECT * FROM meta_unit");
        List<Map> jsonMap = new LinkedList<>();
        for (Record record : unitRecords) {
            Map<String, Object> columns = record.getColumns();
            jsonMap.add(columns);
        }
        IResultSet resultSet = new ResultSet(jsonMap);
        return resultSet;
    }


    @Override
    public IResultSet retrieveAddress(String pid) {
        IResultSet resultSet = new ResultSet();
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        if (pid == null) {
            List<Record> records = Db.find("SELECT * FROM meta_address WHERE code = pcode");
            for (Record record : records) {
                result.add(record.getColumns());
            }
        } else {
            List<Record> records = Db.find("SELECT * FROM meta_address WHERE pcode = ? AND code != pcode", pid);
            for (Record record : records) {
                Map<String, Object> addressEntity = record.getColumns();
                String status = addressEntity.get("status").toString();
                if (status.equals("0")) {
                    List<Record> subAddressEntities = Db.find("SELECT * FROM meta_address WHERE pcode = ?", addressEntity.get("code"));
                    for (Record subAddressEntity : subAddressEntities) {
                        result.add(subAddressEntity.getColumns());
                    }
                } else {
                    result.add(record.getColumns());
                }
            }
        }
        resultSet.setCode(200);
        resultSet.setData(result);
        return resultSet;
    }

    @Override
    public IResultSet retrieveLink() {
        IResultSet resultSet = new ResultSet();
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM link WHERE linkId = pid ORDER BY queue");
        for (Record record : records) {
            Map<String, Object> linkEntity = record.getColumns();
            LinkedList<Map<String, Object>> subResult = new LinkedList<>();
            List<Record> subRecords = Db.find("SELECT * FROM link WHERE pid = ? AND linkId != pid" , linkEntity.get("pid"));
            for (Record subRecord:subRecords){
                subResult.add(subRecord.getColumns());
            }
            linkEntity.put("children", subResult);
            result.add(linkEntity);
        }
        resultSet.setCode(200);
        resultSet.setData(result);
        return resultSet;
    }
}
