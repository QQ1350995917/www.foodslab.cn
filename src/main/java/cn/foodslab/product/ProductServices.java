package cn.foodslab.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by Pengwei Ding on 2016-07-30 21:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ProductServices implements IProductServices {

    @Override
    public IResultSet series(String flag, String seriesId) {
        LinkedList<Map> seriesList = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE status != -1");
        for (int index = 0; index < seriesRecords.size(); index++) {
            Map<String, Object> seriesMap = seriesRecords.get(index).getColumns();
            if (flag != null && !flag.trim().equals("")) {
                if ((seriesId == null) && index == 0) {
                    seriesId = seriesMap.get("seriesId").toString();
                }
                if (seriesMap.get("seriesId").toString().equals(seriesId)) {
                    List<Record> typeRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE seriesId='" + seriesMap.get("seriesId") + "' AND status != -1");
                    LinkedList<Map> typeList = new LinkedList<>();
                    for (Record typeRecord : typeRecords) {
                        Map<String, Object> typeMap = typeRecord.getColumns();
                        List<Record> formatRecords = Db.find("SELECT formatId,label,meta,price,priceMeta,typeId FROM product_format WHERE typeId='" + typeMap.get("typeId") + "' AND status != -1");
                        LinkedList<Map> formatList = new LinkedList<>();
                        for (Record formatRecord : formatRecords) {
                            Map<String, Object> formatMap = formatRecord.getColumns();
                            formatList.add(formatMap);
                        }
                        typeMap.put("children", formatList);
                        typeList.add(typeMap);
                    }
                    seriesMap.put("children", typeList);
                }
            }
            seriesList.add(seriesMap);
        }
        IResultSet resultSet = new ResultSet(seriesList);
        resultSet.setCode(200);
        return resultSet;
    }

    @Override
    public IResultSet type(String typeId) {
        List<Record> typeRecords = Db.find("SELECT typeId,label,description,detail,seriesId FROM product_type WHERE typeId = ? AND status = 1", typeId);
        Record typeRecord = typeRecords.get(0);
        Map<String, Object> typeEntity = typeRecord.getColumns();

        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId = ? AND status = 1", typeId);
        LinkedList<Map> formatList = new LinkedList<>();
        for (Record formatRecord : formatRecords) {
            Map<String, Object> formatMap = formatRecord.getColumns();
            formatList.add(formatMap);
        }
        typeEntity.put("children", formatList);

        List<Record> imageRecords = Db.find("SELECT * FROM image WHERE trunkId = ? AND status = 1", typeId);
        LinkedList<Map> imageList = new LinkedList<>();
        for (Record imageRecord : imageRecords) {
            Map<String, Object> formatMap = imageRecord.getColumns();
            imageList.add(formatMap);
        }
        typeEntity.put("images", imageList);

        List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ? AND status = 1", typeRecord.get("seriesId").toString());
        Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
        seriesEntity.put("child", typeEntity);

        IResultSet resultSet = new ResultSet(seriesEntity);
        resultSet.setCode(200);
        return resultSet;
    }

    @Override
    public IResultSet format(String... formatIds) {
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        for(String formatId:formatIds){
            List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE formatId = ? AND status = 1",formatId);
            Record formatRecord = formatRecords.get(0);
            Map<String, Object> formatEntity = formatRecord.getColumns();
            List<Record> typeRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ? AND status = 1", formatRecord.get("typeId").toString());
            Record typeRecord = typeRecords.get(0);
            Map<String, Object> typeEntity = typeRecord.getColumns();
            formatEntity.put("parent", typeEntity);
            List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ? AND status = 1", typeRecord.get("seriesId").toString());
            Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
            typeEntity.put("parent", seriesEntity);
            result.add(formatEntity);
        }
        IResultSet resultSet = new ResultSet(result);
        resultSet.setCode(200);
        return resultSet;
    }

    @Override
    public IResultSet recommend() {
        List<Record> formatRecords = Db.find("SELECT formatId,label,meta,price,priceMeta,typeId FROM product_format WHERE status = 1 AND weight < 8 order by weight ASC");
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        for (Record formatRecord : formatRecords) {
            Map<String, Object> formatEntity = formatRecord.getColumns();
            List<Record> typeIdRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ?", formatEntity.get("typeId"));
            Map<String, Object> typeEntity = typeIdRecords.get(0).getColumns();
            List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ?", typeEntity.get("seriesId"));
            Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
            typeEntity.put("parent", seriesEntity);
            formatEntity.put("parent", typeEntity);
            result.add(formatEntity);
        }
        IResultSet resultSet = new ResultSet(result);
        resultSet.setCode(200);
        return resultSet;
    }

    @Override
    public IResultSet createSeries(SeriesEntity seriesEntity) {
        IResultSet resultSet = isExistSeriesName(seriesEntity.getLabel());
        if (resultSet.getData().equals("true")) {
            resultSet.setCode(400);
            resultSet.setMessage("系列名称已经存在");
        } else if (resultSet.getData().equals("false")) {
            String seriesId = UUID.randomUUID().toString();
            Record record = new Record().set("seriesId", seriesId).set("label", seriesEntity.getLabel());
            Db.save("product_series", record);
            resultSet.setCode(200);
            resultSet.setMessage("创建系列成功");
            HashMap<String, String> series = new HashMap<>();
            series.put("seriesId", seriesId);
            series.put("label", seriesEntity.getLabel());
            series.put("status", "1");
            resultSet.setData(series);
        } else {
            resultSet.setCode(501);
            resultSet.setMessage("检测出的结果超出正常值范围");
        }
        return resultSet;
    }

    @Override
    public IResultSet createType(TypeEntity typeEntity) {
        IResultSet resultSet = isExistTypeNameInSeries(typeEntity.getSeriesId(), typeEntity.getLabel());
        if (resultSet.getData().toString().equals("true")) {
            resultSet.setCode(400);
            resultSet.setMessage("型号名称已经存在");
        } else if (resultSet.getData().toString().equals("false")) {
            String typeId = UUID.randomUUID().toString();
            Record record = new Record().set("seriesId", typeEntity.getSeriesId()).set("typeId", typeId).set("label", typeEntity.getLabel());
            Db.save("product_type", record);
            resultSet.setCode(200);
            resultSet.setMessage("创建系列成功");
            HashMap<String, String> typeInSeries = new HashMap<>();
            typeInSeries.put("seriesId", typeEntity.getSeriesId());
            typeInSeries.put("typeId", typeId);
            typeInSeries.put("label", typeEntity.getLabel());
            resultSet.setData(typeInSeries);
        } else {
            resultSet.setCode(501);
            resultSet.setMessage("检测出的结果超出正常值范围");
        }
        return resultSet;
    }

    @Override
    public IResultSet createFormat(FormatEntity formatEntity) {
        IResultSet resultSet = isExistFormatNameInType(formatEntity.getTypeId(), formatEntity.getLabel(), formatEntity.getMeta());
        if (resultSet.getData().equals("true")) {
            resultSet.setCode(400);
            resultSet.setMessage("规格名称已经存在");
        } else if (resultSet.getData().equals("false")) {
            String formatId = UUID.randomUUID().toString();
            Record record = new Record().set("formatId", formatId)
                    .set("label", formatEntity.getLabel())
                    .set("meta", formatEntity.getMeta())
                    .set("amount", formatEntity.getAmount())
                    .set("amountMeta", formatEntity.getAmountMeta())
                    .set("price", formatEntity.getPricing())
                    .set("priceMeta", formatEntity.getPriceMeta())
                    .set("postage", formatEntity.getPostage())
                    .set("postageMeta", formatEntity.getPostageMeta())
                    .set("pricing", formatEntity.getPrice())
                    .set("pricingDiscount", formatEntity.getPricingDiscount())
                    .set("pricingStart", formatEntity.getPricingStart())
                    .set("pricingEnd", formatEntity.getPricingEnd())
                    .set("pricingStatus", formatEntity.getPricingStatus())
                    .set("expressCount", formatEntity.getExpressCount())
                    .set("expressName", formatEntity.getExpressName())
                    .set("expressStart", formatEntity.getExpressStart())
                    .set("expressEnd", formatEntity.getExpressEnd())
                    .set("expressStatus", formatEntity.getExpressStatus())
                    .set("giftCount", formatEntity.getGiftCount())
                    .set("giftLabel", formatEntity.getGiftLabel())
                    .set("giftId", formatEntity.getGiftId())
                    .set("giftStart", formatEntity.getGiftStart())
                    .set("giftEnd", formatEntity.getGiftEnd())
                    .set("giftStatus", formatEntity.getGiftStatus())
                    .set("queue", formatEntity.getQueue())
                    .set("status", formatEntity.getStatus())
                    .set("typeId", formatEntity.getTypeId());
            Db.save("product_format", record);
            resultSet.setCode(200);
            resultSet.setMessage("创建规格成功");
            HashMap<String, String> typeInSeries = new HashMap<>();
            typeInSeries.put("typeId", formatEntity.getTypeId());
            typeInSeries.put("formatId", formatId);
            typeInSeries.put("label", formatEntity.getLabel());
            typeInSeries.put("meta", formatEntity.getMeta());
            resultSet.setData(typeInSeries);
        } else {
            resultSet.setCode(501);
            resultSet.setMessage("检测出的结果超出正常值范围");
        }
        return resultSet;
    }

    @Override
    public IResultSet updateSeries(SeriesEntity seriesEntity) {
        IResultSet resultSet = isExistSeriesName(seriesEntity.getLabel(), seriesEntity.getSeriesId());
        int update = Db.update("UPDATE product_series SET label = ? , status = ? WHERE seriesId = ? ", seriesEntity.getLabel(), seriesEntity.getStatus(), seriesEntity.getSeriesId());
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet updateType(TypeEntity typeEntity) {
        IResultSet resultSet = isExistTypeNameInSeries(typeEntity.getSeriesId(), typeEntity.getLabel(), typeEntity.getTypeId());
        int update = Db.update("UPDATE product_type SET label = ? , status = ? WHERE typeId = ? ", typeEntity.getLabel(), typeEntity.getStatus(), typeEntity.getTypeId());
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet updateTypeDescription(TypeEntity typeEntity) {
        int update = Db.update("UPDATE product_type SET description = ? ,detail = ? where typeId = ? ", typeEntity.getDescription(), typeEntity.getDetail(), typeEntity.getTypeId());
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

    @Override
    public IResultSet updateTypeImage(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public IResultSet updateFormat(FormatEntity formatEntity) {
        return null;
    }

    @Override
    public IResultSet retrieve() {
        LinkedList<Map> seriesList = new LinkedList<>();
        List<Record> seriesRecords = Db.find("SELECT * FROM product_series WHERE status != -1");
        for (Record seriesRecord : seriesRecords) {
            Map<String, Object> seriesMap = seriesRecord.getColumns();
            List<Record> typeRecords = Db.find("SELECT * FROM product_type WHERE seriesId='" + seriesMap.get("seriesId") + "' AND status != -1");
            LinkedList<Map> typeList = new LinkedList<>();
            for (Record typeRecord : typeRecords) {
                Map<String, Object> typeMap = typeRecord.getColumns();
                List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId='" + typeMap.get("typeId") + "' AND status != -1");
                LinkedList<Map> formatList = new LinkedList<>();
                for (Record formatRecord : formatRecords) {
                    Map<String, Object> formatMap = formatRecord.getColumns();
                    formatList.add(formatMap);
                }
                typeMap.put("children", formatList);
                typeList.add(typeMap);
            }
            seriesMap.put("children", typeList);
            seriesList.add(seriesMap);
        }
        IResultSet resultSet = new ResultSet(seriesList);
        return resultSet;
    }

    @Override
    public IResultSet convert() {
        List<Record> formatRecords = Db.find("SELECT formatId,typeId,label,meta,weight FROM product_format WHERE status != -1 order by weight ASC, updateTime DESC");
        LinkedList<Map<String, Object>> result = new LinkedList<>();
        for (Record formatRecord : formatRecords) {
            Map<String, Object> formatEntity = formatRecord.getColumns();
            List<Record> typeIdRecords = Db.find("SELECT typeId,label,seriesId FROM product_type WHERE typeId = ?", formatEntity.get("typeId"));
            Map<String, Object> typeEntity = typeIdRecords.get(0).getColumns();
            List<Record> seriesRecords = Db.find("SELECT seriesId,label FROM product_series WHERE seriesId = ?", typeEntity.get("seriesId"));
            Map<String, Object> seriesEntity = seriesRecords.get(0).getColumns();
            typeEntity.put("parent", seriesEntity);
            formatEntity.put("parent", typeEntity);
            result.add(formatEntity);
        }
        IResultSet resultSet = new ResultSet(result);
        resultSet.setCode(200);
        return resultSet;
    }

    @Override
    public IResultSet retrieveSeries(String seriesId) {
        List<Record> seriesRecords = Db.find("SELECT * FROM product_series WHERE seriesId = '" + seriesId + "'");
        Map<String, Object> seriesMap = seriesRecords.get(0).getColumns();
        List<Record> typeRecords = Db.find("SELECT * FROM product_type WHERE seriesId='" + seriesMap.get("seriesId") + "'");
        LinkedList<Map> typeList = new LinkedList<>();
        for (Record typeRecord : typeRecords) {
            Map<String, Object> typeMap = typeRecord.getColumns();
            List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId='" + typeMap.get("typeId") + "'");
            LinkedList<Map> formatList = new LinkedList<>();
            for (Record formatRecord : formatRecords) {
                Map<String, Object> formatMap = formatRecord.getColumns();
                formatList.add(formatMap);
            }
            typeMap.put("children", formatList);
            typeList.add(typeMap);
        }
        seriesMap.put("children", typeList);
        IResultSet resultSet = new ResultSet(seriesMap);
        return resultSet;
    }

    @Override
    public IResultSet retrieveType(String typeId) {
        List<Record> typeRecords = Db.find("SELECT * FROM product_type WHERE typeId='" + typeId + "'");
        Map<String, Object> typeMap = typeRecords.get(0).getColumns();
        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE typeId='" + typeMap.get("typeId") + "'");
        LinkedList<Map> formatList = new LinkedList<>();
        for (Record formatRecord : formatRecords) {
            Map<String, Object> formatMap = formatRecord.getColumns();
            formatList.add(formatMap);
        }
        typeMap.put("children", formatList);
        IResultSet resultSet = new ResultSet(typeMap);
        return resultSet;
    }

    @Override
    public IResultSet retrieveFormat(String formatId) {
        List<Record> formatRecords = Db.find("SELECT * FROM product_format WHERE formatId='" + formatId + "'");
        Map<String, Object> formatMap = formatRecords.get(0).getColumns();
        IResultSet resultSet = new ResultSet(formatMap);
        return resultSet;
    }

    @Override
    public IResultSet updateWeight(FormatEntity formatEntity) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int updateAll = Db.update("UPDATE product_format SET weight = weight + 1");
                int update = Db.update("UPDATE product_format SET weight = ? WHERE formatId = ?", formatEntity.getWeight(), formatEntity.getFormatId());
                return updateAll > 0 && update == 1;
            }
        });
        if (succeed) {
            return convert();
        } else {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
            return resultSet;
        }
    }

    @Override
    public IResultSet swapWeight(FormatEntity formatEntity1, FormatEntity formatEntity2) {
        boolean succeed = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                int update1 = Db.update("UPDATE product_format SET weight = ? WHERE formatId = ?", formatEntity1.getWeight(), formatEntity2.getFormatId());
                int update2 = Db.update("UPDATE product_format SET weight = ? WHERE formatId = ?", formatEntity2.getWeight(), formatEntity1.getFormatId());
                return update1 > 0 && update2 == 1;
            }
        });
        IResultSet resultSet = new ResultSet();
        if (succeed) {
            resultSet.setCode(200);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }


    private IResultSet isExistSeriesName(String seriesName) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE label = '" + seriesName + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }

    private IResultSet isExistSeriesName(String seriesName, String noSeriesId) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE label = '" + seriesName + "' AND seriesId != '" + noSeriesId + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }

    private IResultSet isExistTypeNameInSeries(String seriesId, String typeName) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE label = '" + typeName + "' AND seriesId = '" + seriesId + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }

    private IResultSet isExistTypeNameInSeries(String seriesId, String typeName, String noTypeId) {
        List<Record> records = Db.find("SELECT * FROM product_type WHERE label = '" + typeName + "' AND seriesId = '" + seriesId + "' AND typeId != '" + noTypeId + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }

    private IResultSet isExistFormatNameInType(String typeId, String label, String meta) {
        List<Record> records = Db.find("SELECT * FROM product_format WHERE label = '" + label + "' AND meta = '" + meta + "' AND typeId = '" + typeId + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }


}
