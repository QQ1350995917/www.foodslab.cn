package cn.foodslab.back.link;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LinkServices implements ILinkServices {

    @Override
    public IResultSet retrieve() {
        LinkedList<Map> linkList = new LinkedList<>();
        List<Record> linkRecords = Db.find("SELECT * FROM link WHERE linkId = pid");
        for (Record linkRecord : linkRecords) {
            Map<String, Object> linkEntity = linkRecord.getColumns();
            LinkedList<Map> subLinkList = new LinkedList<>();
            List<Record> subLinkRecords = Db.find("SELECT * FROM link WHERE pid = ? AND status != -1 AND linkId != pid", linkEntity.get("linkId"));
            for (Record subLinkRecord : subLinkRecords) {
                Map<String, Object> subLinkEntity = subLinkRecord.getColumns();
                subLinkList.add(subLinkEntity);
            }
            linkEntity.put("children", subLinkList);
            linkList.add(linkEntity);
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(200);
        resultSet.setData(linkList);
        resultSet.setMessage("");
        return resultSet;
    }

    @Override
    public IResultSet create(LinkEntity linkEntity) {
        IResultSet resultSet = isExistLinkName(linkEntity);
        if ("true".equals(resultSet.getData().toString())) {
            resultSet.setCode(500);
            resultSet.setMessage("名称已经存在");
        } else if ("false".equals(resultSet.getData().toString())) {
            Record record = new Record()
                    .set("linkId", linkEntity.getLinkId())
                    .set("label", linkEntity.getLabel())
                    .set("href", linkEntity.getHref())
                    .set("status", linkEntity.getStatus())
                    .set("pid", linkEntity.getPid());
            Db.save("link", record);
            resultSet.setCode(200);
            resultSet.setMessage("创建成功");
            HashMap<String, String> link = new HashMap<>();
            link.put("linkId", linkEntity.getLinkId());
            link.put("label", linkEntity.getLabel());
            link.put("href", linkEntity.getHref());
            link.put("status", linkEntity.getStatus() + "");
            link.put("pid", linkEntity.getPid());
            resultSet.setData(link);
        }
        return resultSet;
    }

    @Override
    public IResultSet update(LinkEntity linkEntity) {
        int update = Db.update("UPDATE link SET label = ?, href = ?, status = ? WHERE linkId = ?", linkEntity.getLabel(), linkEntity.getHref(), linkEntity.getStatus(), linkEntity.getLinkId());
        IResultSet resultSet = new ResultSet();
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }

    private IResultSet isExistLinkName(LinkEntity linkEntity) {
        List<Record> records = Db.find("SELECT * FROM link WHERE status !=-1 AND label = ? AND pid = ?", linkEntity.getLabel(), linkEntity.getLinkId());
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }


}
