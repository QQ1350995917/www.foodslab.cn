package cn.foodslab.controller.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-07-30 21:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ProductController extends Controller {
    IProductServices iProductServices = new ProductServices();

    public void mRetrieves() {
        String params = this.getPara("p");
        Map map = JSON.parseObject(params, Map.class);
        String sessionId = map.get("sessionId").toString();
        LinkedList<SeriesEntity> seriesEntities = iProductServices.mRetrieves();
        LinkedList<VSeriesEntity> vSeriesEntities = new LinkedList<>();
        for (SeriesEntity seriesEntity : seriesEntities) {
            VSeriesEntity vSeriesEntity = new VSeriesEntity(seriesEntity);
            vSeriesEntity.setSessionId(sessionId);
            LinkedList<TypeEntity> typeEntities = seriesEntity.getTypeEntities();
            LinkedList<VTypeEntity> vTypeEntities = new LinkedList<>();
            for (TypeEntity typeEntity : typeEntities) {
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                vTypeEntity.setSessionId(sessionId);
                LinkedList<FormatEntity> formatEntities = typeEntity.getFormatEntities();
                LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
                for (FormatEntity formatEntity : formatEntities) {
                    VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                    vFormatEntity.setSessionId(sessionId);
                    vFormatEntities.add(vFormatEntity);
                }
                vTypeEntity.setChildren(vFormatEntities);
                vTypeEntities.add(vTypeEntity);
            }
            vSeriesEntity.setChildren(vTypeEntities);
            vSeriesEntities.add(vSeriesEntity);
        }
        IResultSet iResultSet = new ResultSet(3050, vSeriesEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }


//    @Override
//    public void series() {
//        IResultSet resultSet = iProductServices.series(this.getPara("flag"),this.getPara("seriesId"));
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void type() {
//        IResultSet resultSet = iProductServices.type(this.getPara("typeId"));
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void format() {
//        String formatIds = this.getPara("formatIds");
//        String[] split = formatIds.split(",");
//        IResultSet resultSet = iProductServices.format(split);
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void recommend() {
//        IResultSet resultSet = iProductServices.recommend();
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void retrieve() {
//        IResultSet resultSet = iProductServices.retrieve();
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void convert() {
//        IResultSet resultSet = iProductServices.convert();
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void retrieveSeries() {
//        String seriesId = this.getPara("seriesId");
//        IResultSet resultSet = iProductServices.retrieveSeries(seriesId);
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void retrieveType() {
//        String typeId = this.getPara("typeId");
//        IResultSet resultSet = iProductServices.retrieveType(typeId);
//        renderJson(JSON.toJSONString(resultSet));
//    }
//
//    @Override
//    public void retrieveFormat() {
//        String formatId = this.getPara("formatId");
//        IResultSet resultSet = iProductServices.retrieveFormat(formatId);
//        renderJson(JSON.toJSONString(resultSet));
//    }


}
