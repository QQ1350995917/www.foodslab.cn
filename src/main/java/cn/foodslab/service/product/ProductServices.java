package cn.foodslab.service.product;

import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 21:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ProductServices implements IProductServices {
    @Override
    public LinkedList<SeriesEntity> mRetrieves() {
        LinkedList<SeriesEntity> seriesEntities = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT * FROM product_series WHERE status != -1");
        for (Record seriesRecord : seriesRecords) {
            SeriesEntity seriesEntity = JSON.parseObject(JSON.toJSONString(seriesRecord.getColumns()), SeriesEntity.class);
            List<Record> typeRecords = Db.find("SELECT * FROM product_type WHERE seriesId = ? AND status != -1", seriesEntity.getSeriesId());
            LinkedList<TypeEntity> typeEntities = new LinkedList<>();
            for (Record typeRecord : typeRecords) {
                TypeEntity typeEntity = JSON.parseObject(JSON.toJSONString(typeRecord.getColumns()), TypeEntity.class);
                List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId = ? AND status != -1", typeEntity.getTypeId());
                LinkedList<FormatEntity> formatEntities = new LinkedList<>();
                for (Record formatRecord : formatRecords) {
                    FormatEntity formatEntity = JSON.parseObject(JSON.toJSONString(formatRecord.getColumns()), FormatEntity.class);
                    formatEntities.add(formatEntity);
                }
                typeEntity.setFormatEntities(formatEntities);
                typeEntities.add(typeEntity);
            }
            seriesEntity.setTypeEntities(typeEntities);
            seriesEntities.add(seriesEntity);
        }
        return seriesEntities;
    }



    //    @Override
//    public FormatEntity retrieveTreeByFormatId(String formatId) {
//        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE formatId = ? AND status = 1", formatId);
//        Map<String, Object> formatMap = formatRecords.get(0).getColumns();
//        FormatEntity formatEntity = JSON.parseObject(JSON.toJSONString(formatMap), FormatEntity.class);
//
//        List<Record> typeRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ? AND status = 1", formatEntity.getTypeId());
//        Map<String, Object> typeMap = typeRecords.get(0).getColumns();
//        TypeEntity typeEntity = JSON.parseObject(JSON.toJSONString(typeMap), TypeEntity.class);
//
//        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ? AND status = 1", typeEntity.getSeriesId());
//        Map<String, Object> seriesMap = seriesRecords.get(0).getColumns();
//        SeriesEntity seriesEntity = JSON.parseObject(JSON.toJSONString(seriesMap), SeriesEntity.class);
//
//        typeEntity.setParent(seriesEntity);
//        formatEntity.setParent(typeEntity);
//
//        return formatEntity;
//    }
//
//    @Override
//    public IResultSet series(String flag, String seriesId) {
//        LinkedList<Map> seriesList = new LinkedList<>();
//        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE status != -1");
//        for (int index = 0; index < seriesRecords.size(); index++) {
//            Map<String, Object> seriesMap = seriesRecords.get(index).getColumns();
//            if (flag != null && !flag.trim().equals("")) {
//                if ((seriesId == null) && index == 0) {
//                    seriesId = seriesMap.get("seriesId").toString();
//                }
//                if (seriesMap.get("seriesId").toString().equals(seriesId)) {
//                    List<Record> typeRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE seriesId='" + seriesMap.get("seriesId") + "' AND status != -1");
//                    LinkedList<Map> typeList = new LinkedList<>();
//                    for (Record typeRecord : typeRecords) {
//                        Map<String, Object> typeMap = typeRecord.getColumns();
//                        List<Record> formatRecords = Db.find("SELECT formatId,label,meta,price,priceMeta,typeId FROM product_format WHERE typeId='" + typeMap.get("typeId") + "' AND status != -1");
//                        LinkedList<Map> formatList = new LinkedList<>();
//                        for (Record formatRecord : formatRecords) {
//                            Map<String, Object> formatMap = formatRecord.getColumns();
//                            formatList.add(formatMap);
//                        }
//                        typeMap.put("children", formatList);
//                        typeList.add(typeMap);
//                    }
//                    seriesMap.put("children", typeList);
//                }
//            }
//            seriesList.add(seriesMap);
//        }
//        IResultSet resultSet = new ResultSet(seriesList);
//        resultSet.setCode(200);
//        return resultSet;
//    }
//
//    @Override
//    public IResultSet type(String typeId) {
//        List<Record> typeRecords = Db.find("SELECT typeId,label,description,detail,seriesId FROM product_type WHERE typeId = ? AND status = 1", typeId);
//        Record typeRecord = typeRecords.get(0);
//        Map<String, Object> typeEntity = typeRecord.getColumns();
//
//        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId = ? AND status = 1", typeId);
//        LinkedList<Map> formatList = new LinkedList<>();
//        for (Record formatRecord : formatRecords) {
//            Map<String, Object> formatMap = formatRecord.getColumns();
//            formatList.add(formatMap);
//        }
//        typeEntity.put("children", formatList);
//
//        List<Record> imageRecords = Db.find("SELECT * FROM image WHERE trunkId = ? AND status = 1", typeId);
//        LinkedList<Map> imageList = new LinkedList<>();
//        for (Record imageRecord : imageRecords) {
//            Map<String, Object> formatMap = imageRecord.getColumns();
//            imageList.add(formatMap);
//        }
//        typeEntity.put("images", imageList);
//
//        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ? AND status = 1", typeRecord.get("seriesId").toString());
//        Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
//        seriesEntity.put("child", typeEntity);
//
//        IResultSet resultSet = new ResultSet(seriesEntity);
//        resultSet.setCode(200);
//        return resultSet;
//    }

//    @Override
//    public IResultSet format(String... formatIds) {
//        LinkedList<Map<String, Object>> result = new LinkedList<>();
//        for(String formatId:formatIds){
//            List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE formatId = ? AND status = 1",formatId);
//            Record formatRecord = formatRecords.get(0);
//            Map<String, Object> formatEntity = formatRecord.getColumns();
//            List<Record> typeRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ? AND status = 1", formatRecord.get("typeId").toString());
//            Record typeRecord = typeRecords.get(0);
//            Map<String, Object> typeEntity = typeRecord.getColumns();
//            formatEntity.put("parent", typeEntity);
//            List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ? AND status = 1", typeRecord.get("seriesId").toString());
//            Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
//            typeEntity.put("parent", seriesEntity);
//            result.add(formatEntity);
//        }
//        IResultSet resultSet = new ResultSet(result);
//        resultSet.setCode(200);
//        return resultSet;
//    }

//    @Override
//    public IResultSet recommend() {

//    }
//
//    @Override
//    public IResultSet convert() {
//        List<Record> formatRecords = Db.find("SELECT formatId,typeId,label,meta,weight FROM product_format WHERE status != -1 order by weight ASC, updateTime DESC");
//        LinkedList<Map<String, Object>> result = new LinkedList<>();
//        for (Record formatRecord : formatRecords) {
//            Map<String, Object> formatEntity = formatRecord.getColumns();
//            List<Record> typeIdRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ?", formatEntity.get("typeId"));
//            Map<String, Object> typeEntity = typeIdRecords.get(0).getColumns();
//            List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ?", typeEntity.get("seriesId"));
//            Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
//            typeEntity.put("parent", seriesEntity);
//            formatEntity.put("parent", typeEntity);
//            result.add(formatEntity);
//        }
//        IResultSet resultSet = new ResultSet(result);
//        resultSet.setCode(200);
//        return resultSet;
//    }
//
//    @Override
//    public IResultSet retrieveSeries(String seriesId) {
//        List<Record> seriesRecords = Db.find("SELECT * FROM product_series WHERE seriesId = '" + seriesId + "'");
//        Map<String, Object> seriesMap = seriesRecords.get(0).getColumns();
//        List<Record> typeRecords = Db.find("SELECT * FROM product_type WHERE seriesId='" + seriesMap.get("seriesId") + "'");
//        LinkedList<Map> typeList = new LinkedList<>();
//        for (Record typeRecord : typeRecords) {
//            Map<String, Object> typeMap = typeRecord.getColumns();
//            List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId='" + typeMap.get("typeId") + "'");
//            LinkedList<Map> formatList = new LinkedList<>();
//            for (Record formatRecord : formatRecords) {
//                Map<String, Object> formatMap = formatRecord.getColumns();
//                formatList.add(formatMap);
//            }
//            typeMap.put("children", formatList);
//            typeList.add(typeMap);
//        }
//        seriesMap.put("children", typeList);
//        IResultSet resultSet = new ResultSet(seriesMap);
//        return resultSet;
//    }
}
