package cn.foodslab.service.product;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-22 17:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品系列接口
 * 使用m标记的接口为后台接口
 */
public class SeriesServices implements ISeriesServices {

    @Override
    public SeriesEntity mCreate(SeriesEntity seriesEntity) {
        if (this.retrieveByLabel(seriesEntity) == null) {
            Record record = new Record().set("seriesId", seriesEntity.getSeriesId()).set("label", seriesEntity.getLabel());
            boolean save = Db.save("product_series", record);
            if (save) {
                return seriesEntity;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public SeriesEntity mUpdate(SeriesEntity seriesEntity) {
        if (this.retrieveByLabel(seriesEntity) == null) {
            int update = Db.update("UPDATE product_series SET label = ? WHERE seriesId = ? ", seriesEntity.getLabel(), seriesEntity.getSeriesId());
            if (update == 1) {
                return seriesEntity;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public SeriesEntity mUpdateStatus(SeriesEntity seriesEntity) {
        int update = Db.update("UPDATE product_series SET status = ? WHERE seriesId = ? ", seriesEntity.getStatus(), seriesEntity.getSeriesId());
        if (update == 1) {
            return seriesEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<SeriesEntity> retrieves() {
        LinkedList<SeriesEntity> seriesEntities = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE status = 1");
        for (int index = 0; index < seriesRecords.size(); index++) {
            Map<String, Object> seriesMap = seriesRecords.get(index).getColumns();
            SeriesEntity result = JSON.parseObject(JSON.toJSONString(seriesMap), SeriesEntity.class);
            seriesEntities.add(result);
        }
        return seriesEntities;
    }

    @Override
    public LinkedList<SeriesEntity> mRetrieves() {
        LinkedList<SeriesEntity> seriesEntities = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE status != -1");
        for (int index = 0; index < seriesRecords.size(); index++) {
            Map<String, Object> seriesMap = seriesRecords.get(index).getColumns();
            SeriesEntity result = JSON.parseObject(JSON.toJSONString(seriesMap), SeriesEntity.class);
            seriesEntities.add(result);
        }
        return seriesEntities;
    }

    @Override
    public SeriesEntity retrieveById(String seriesId) {
        return null;
    }

    @Override
    public SeriesEntity retrieveByLabel(SeriesEntity seriesEntity) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE label = ? AND seriesId != ? AND status != -1", seriesEntity.getLabel(), seriesEntity.getSeriesId());
        if (records.size() == 1) {
            SeriesEntity result = JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), SeriesEntity.class);
            return result;
        } else {
            return null;
        }
    }
}
