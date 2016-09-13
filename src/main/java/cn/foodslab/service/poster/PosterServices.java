package cn.foodslab.service.poster;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterServices implements IPosterServices {

    @Override
    public IResultSet retrieve() {
        List<Record> posterRecord = Db.find("SELECT * FROM poster WHERE posterId = pid");
        Map<String, Object> posterMap = posterRecord.get(0).getColumns();
        List<Record> posterRecords = Db.find("SELECT * FROM poster WHERE pid = ? AND posterId != pid", posterMap.get("posterId"));
        LinkedList<Map> posterEntities = new LinkedList<>();
        for (Record record : posterRecords) {
            Map<String, Object> posterEntity = record.getColumns();
            posterEntities.add(posterEntity);
        }
        posterMap.put("children", posterEntities);
        IResultSet resultSet = new ResultSet(posterMap);
        return resultSet;
    }

    @Override
    public IResultSet create(PosterEntity posterEntity) {
        Record record = new Record()
                .set("posterId", posterEntity.getPosterId())
                .set("status", posterEntity.getStatus())
                .set("clickable", posterEntity.getClickable())
                .set("href", posterEntity.getHref())
                .set("pid", posterEntity.getPid());
        boolean poster = Db.save("poster", record);
        IResultSet resultSet = new ResultSet();
        if (poster) {
            resultSet.setCode(200);
            resultSet.setMessage("创建成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet update(PosterEntity posterEntity) {
        String head = "UPDATE poster SET status = " + posterEntity.getStatus();
        String set = "";
        if (posterEntity.getStart() != null){
            set = set + " , start = '" + posterEntity.getStart() + "' ";
        }
        if (posterEntity.getEnd() != null){
            set = set + " , end = '" + posterEntity.getEnd() + "' ";
        }
        if (posterEntity.getHref() != null){
            set = set + " , href = '" + posterEntity.getHref() + "' ";
        }
        set = set + " , clickable = '" + posterEntity.getClickable() + "' WHERE posterId = '" + posterEntity.getPosterId() + "'";

        String sql = head + set;

        int update = Db.update(sql);
        IResultSet resultSet = new ResultSet();
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setData(posterEntity);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }
}
