package cn.foodslab.back.product;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
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
                    .set("pricing", formatEntity.getPricing())
                    .set("pricingMeta", formatEntity.getPricingMeta())
                    .set("postage", formatEntity.getPostage())
                    .set("postageMeta", formatEntity.getPostageMeta())
                    .set("price", formatEntity.getPrice())
                    .set("priceDiscount", formatEntity.getPriceDiscount())
                    .set("priceStart", formatEntity.getPriceStart())
                    .set("priceEnd", formatEntity.getPriceEnd())
                    .set("priceStatus", formatEntity.getPriceStatus())
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
        return null;
    }

    @Override
    public IResultSet updateType(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public IResultSet updateTypeDescription(TypeEntity typeEntity) {
        boolean succeed = Db.tx(new IAtom(){
            public boolean run() throws SQLException {
                int count = Db.update("update account set cash = cash - ? where id = ?", 100, 123);
                int count2 = Db.update("update account set cash = cash + ? where id = ?", 100, 456);
                return count == 1 && count2 == 1;
            }});
        return null;
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
        List<Record> seriesRecords = Db.find("SELECT * FROM product_series");
        for (Record seriesRecord : seriesRecords) {
            Map<String, Object> seriesMap = seriesRecord.getColumns();
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
            seriesList.add(seriesMap);
        }
        IResultSet resultSet = new ResultSet(seriesList);
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

    private IResultSet isExistSeriesName(String seriesName) {
        List<Record> records = Db.find("SELECT * FROM product_series WHERE label = '" + seriesName + "'");
        System.out.println("debug = " + records.size());
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


    private IResultSet isExistFormatNameInType(String typeId, String label, String meta) {
        List<Record> records = Db.find("SELECT * FROM product_format WHERE label = '" + label + "' AND meta = '" + meta + "' AND typeId = '" + typeId + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }
}
