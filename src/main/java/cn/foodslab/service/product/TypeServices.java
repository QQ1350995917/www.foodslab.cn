package cn.foodslab.service.product;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-22 17:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeServices implements ITypeServices {

    @Override
    public TypeEntity mCreate(TypeEntity typeEntity) {
        if (this.mRetrieveInSeriesByLabel(typeEntity) == null) {
            Record record = new Record().set("seriesId", typeEntity.getSeriesId()).set("typeId", typeEntity.getTypeId()).set("label", typeEntity.getLabel());
            boolean save = Db.save("product_type", record);
            if (save) {
                return typeEntity;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdate(TypeEntity typeEntity) {
        if (this.mRetrieveInSeriesByLabel(typeEntity) == null) {
            int update = Db.update("UPDATE product_type SET label = ? WHERE typeId = ? ", typeEntity.getLabel(), typeEntity.getTypeId());
            if (update == 1) {
                return typeEntity;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public TypeEntity mUpdateStatus(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET status = ? WHERE typeId = ? ", typeEntity.getStatus(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public LinkedList<TypeEntity> mRetrieveInSeries(String seriesId) {
        LinkedList<TypeEntity> typeEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_type WHERE seriesId = ? WHERE status != -1", seriesId);
        for (Record record : records) {
            Map<String, Object> typeMap = record.getColumns();
            typeEntities.add(JSON.parseObject(JSON.toJSONString(typeMap), TypeEntity.class));
        }
        return typeEntities;
    }

    @Override
    public LinkedList<TypeEntity> retrieveInSeries(String seriesId) {
        LinkedList<TypeEntity> typeEntities = new LinkedList<>();
        List<Record> records = Db.find("SELECT * FROM product_type WHERE seriesId = ? WHERE status = 1", seriesId);
        for (Record record : records) {
            Map<String, Object> typeMap = record.getColumns();
            typeEntities.add(JSON.parseObject(JSON.toJSONString(typeMap), TypeEntity.class));
        }
        return typeEntities;
    }

    @Override
    public TypeEntity mRetrieveById(String typeId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE typeId = ? WHERE status != -1", typeId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), TypeEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity retrieveById(String typeId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE typeId = ? WHERE status = 1", typeId);
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), TypeEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mRetrieveInSeriesByLabel(TypeEntity typeEntity) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE label = ? AND seriesId = ? AND status != -1 AND typeId != ?", typeEntity.getLabel(), typeEntity.getSeriesId(), typeEntity.getTypeId());
        if (records.size() == 1) {
            return JSON.parseObject(JSON.toJSONString(records.get(0).getColumns()), TypeEntity.class);
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdateSummary(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET summary = ? where typeId = ? AND status != -1", typeEntity.getSummary(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }

    @Override
    public TypeEntity mUpdateDirections(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET directions = ? where typeId = ? AND status != -1", typeEntity.getDirections(), typeEntity.getTypeId());
        if (update == 1) {
            return typeEntity;
        } else {
            return null;
        }
    }
}
