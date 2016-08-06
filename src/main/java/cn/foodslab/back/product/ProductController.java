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
    public void createFormat() {
        String typeId = this.getPara("typeId");
        String label = this.getPara("label");
        String meta = this.getPara("meta");
        int amount = this.getParaToInt("amount");
        String amountMeta = this.getPara("amountMeta");
        float pricing = this.getParaToInt("pricing");
        String pricingMeta = this.getPara("pricingMeta");
        float postage = this.getParaToInt("postage");
        String postageMeta = this.getPara("postageMeta");

        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setTypeId(typeId);
        formatEntity.setLabel(label);
        formatEntity.setMeta(meta);
        formatEntity.setAmount(amount);
        formatEntity.setAmountMeta(amountMeta);
        formatEntity.setPricing(pricing);
        formatEntity.setPricingMeta(pricingMeta);
        formatEntity.setPostage(postage);
        formatEntity.setPostageMeta(postageMeta);

        IResultSet resultSet = iProductServices.createFormat(formatEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {

    }
}
