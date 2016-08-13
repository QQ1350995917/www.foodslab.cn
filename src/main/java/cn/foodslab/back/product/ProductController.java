package cn.foodslab.back.product;

import cn.foodslab.back.common.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-30 21:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ProductController extends Controller implements IProductController{
    IProductServices iProductServices = new ProductServices();

    @Override
    public void index() {
        IResultSet resultSet = iProductServices.retrieve();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void convert() {
        IResultSet resultSet = iProductServices.convert();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void retrieveSeries() {
        String seriesId = this.getPara("seriesId");
        IResultSet resultSet = iProductServices.retrieveSeries(seriesId);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void retrieveType() {
        String typeId = this.getPara("typeId");
        IResultSet resultSet = iProductServices.retrieveType(typeId);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void retrieveFormat() {
        String formatId = this.getPara("formatId");
        IResultSet resultSet = iProductServices.retrieveFormat(formatId);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void createSeries() {
        String seriesLabel = this.getPara("label");
        SeriesEntity seriesEntity = new SeriesEntity(seriesLabel);
        IResultSet resultSet = iProductServices.createSeries(seriesEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void updateSeries() {
        String seriesId = this.getPara("seriesId");
        String label = this.getPara("label");
        int status = this.getParaToInt("status");
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setSeriesId(seriesId);
        seriesEntity.setLabel(label);
        seriesEntity.setStatus(status);
        IResultSet resultSet = iProductServices.updateSeries(seriesEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void createType() {
        String seriesId = this.getPara("seriesId");
        String typeLabel = this.getPara("label");
        TypeEntity typeEntity = new TypeEntity(seriesId,typeLabel);
        IResultSet resultSet = iProductServices.createType(typeEntity);
        renderJson(JSON.toJSONString(resultSet));
    }


    @Override
    public void updateType() {
        String seriesId = this.getPara("seriesId");
        String typeId = this.getPara("typeId");
        String label = this.getPara("label");
        int status = this.getParaToInt("status");
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setSeriesId(seriesId);
        typeEntity.setTypeId(typeId);
        typeEntity.setLabel(label);
        typeEntity.setStatus(status);
        IResultSet resultSet = iProductServices.updateType(typeEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void updateTypeDetail() {
        String seriesId = this.getPara("seriesId");
        String typeId = this.getPara("typeId");
        String description = this.getPara("description");
        String detail = this.getPara("detail");
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setSeriesId(seriesId);
        typeEntity.setTypeId(typeId);
        typeEntity.setDescription(description);
        typeEntity.setDetail(detail);
        IResultSet resultSet = iProductServices.updateTypeDescription(typeEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void createFormat() {
        String typeId = this.getPara("typeId");
        int status = this.getParaToInt("status");
        String label = this.getPara("label");
        String meta = this.getPara("meta");
        int amount = this.getParaToInt("amount");
        String amountMeta = this.getPara("amountMeta");
        float pricing = this.getParaToInt("pricing");
        String pricingMeta = this.getPara("pricingMeta");
        float postage = this.getParaToInt("postage");
        String postageMeta = this.getPara("postageMeta");

        int priceStatus = this.getParaToInt("priceStatus");
        float priceDiscount = this.getParaToInt("priceDiscount");
        float price = this.getParaToInt("price");
        String priceStart = this.getPara("priceStart");
        String priceEnd = this.getPara("priceEnd");

        int expressStatus = this.getParaToInt("expressStatus");
        int expressCount = this.getParaToInt("expressCount");
        String expressName = this.getPara("expressName");
        String expressStart = this.getPara("expressStart");
        String expressEnd = this.getPara("expressEnd");

        int giftStatus = this.getParaToInt("giftStatus");
        int giftCount = this.getParaToInt("giftCount");
        String giftLabel = this.getPara("giftLabel");
        String giftStart = this.getPara("giftStart");
        String giftEnd = this.getPara("giftEnd");

        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setTypeId(typeId);
        formatEntity.setStatus(status);
        formatEntity.setLabel(label);
        formatEntity.setMeta(meta);
        formatEntity.setAmount(amount);
        formatEntity.setAmountMeta(amountMeta);
        formatEntity.setPricing(pricing);
        formatEntity.setPricingMeta(pricingMeta);
        formatEntity.setPostage(postage);
        formatEntity.setPostageMeta(postageMeta);

        formatEntity.setPriceStatus(priceStatus);
        formatEntity.setPriceDiscount(priceDiscount);
        formatEntity.setPrice(price);
        formatEntity.setPriceStart(priceStart);
        formatEntity.setPriceEnd(priceEnd);

        formatEntity.setExpressStatus(expressStatus);
        formatEntity.setExpressCount(expressCount);
        formatEntity.setExpressName(expressName);
        formatEntity.setExpressStart(expressStart);
        formatEntity.setExpressEnd(expressEnd);

        formatEntity.setGiftStatus(giftStatus);
        formatEntity.setGiftCount(giftCount);
        formatEntity.setGiftLabel(giftLabel);
        formatEntity.setGiftStart(giftStart);
        formatEntity.setGiftEnd(giftEnd);

        IResultSet resultSet = iProductServices.createFormat(formatEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void updateWeight() {
        String formatId = this.getPara("formatId");
        int weight = this.getParaToInt("weight");
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setFormatId(formatId);
        formatEntity.setWeight(weight);
        IResultSet resultSet = iProductServices.updateWeight(formatEntity);
        renderJson(JSON.toJSONString(resultSet));
    }
}
