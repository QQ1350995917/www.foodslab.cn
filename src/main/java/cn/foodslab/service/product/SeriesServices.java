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
    public LinkedList<SeriesEntity> retrieve() {
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
    public SeriesEntity retrieveById(String seriesId) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE seriesId = ? AND status = 1", seriesId);
        if (records.size() == 1) {
            SeriesEntity result = JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), SeriesEntity.class);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public boolean mExist(String seriesName) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE label = ? AND status != -1", seriesName);
        if (records.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SeriesEntity mCreate(SeriesEntity seriesEntity) {
        if (this.mExist(seriesEntity.getLabel())) {
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
        if (this.mExist(seriesEntity.getLabel())) {
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
    public SeriesEntity mBlock(SeriesEntity seriesEntity) {
        int update = Db.update("UPDATE product_series SET status = 1 WHERE seriesId = ? ", seriesEntity.getSeriesId());
        if (update == 1) {
            return seriesEntity;
        } else {
            return null;
        }
    }

    @Override
    public SeriesEntity mUnBlock(SeriesEntity seriesEntity) {
        int update = Db.update("UPDATE product_series SET status = 2 WHERE seriesId = ? ", seriesEntity.getSeriesId());
        if (update == 1) {
            return seriesEntity;
        } else {
            return null;
        }
    }

    @Override
    public SeriesEntity mDelete(SeriesEntity seriesEntity) {
        return null;
    }

    @Override
    public LinkedList<SeriesEntity> mRetrieves() {
        LinkedList<SeriesEntity> seriesEntities = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT * FROM product_series WHERE status != -1");
        for (int index = 0; index < seriesRecords.size(); index++) {
            Map<String, Object> seriesMap = seriesRecords.get(index).getColumns();
            SeriesEntity result = JSON.parseObject(JSON.toJSONString(seriesMap), SeriesEntity.class);
            seriesEntities.add(result);
        }
        return seriesEntities;
    }
}
