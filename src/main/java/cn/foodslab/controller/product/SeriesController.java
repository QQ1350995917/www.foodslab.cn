package cn.foodslab.controller.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.model.product.VFormatEntity;
import cn.foodslab.model.product.VSeriesEntity;
import cn.foodslab.model.product.VTypeEntity;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-22 16:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SeriesController extends Controller implements ISeriesController {
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        LinkedList<SeriesEntity> seriesEntities = iSeriesServices.retrieve();
        if (seriesEntities == null) {
            IResultSet iResultSet = new ResultSet(3000, null, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<VSeriesEntity> vSeriesEntities = new LinkedList<>();
            for (SeriesEntity seriesEntity : seriesEntities) {
                String sessionId = vSeriesEntity == null ? null : vSeriesEntity.getSessionId();
                Integer status = vSeriesEntity == null ? -2 : vSeriesEntity.getStatus();
                vSeriesEntities.add(new VSeriesEntity(sessionId, seriesEntity.getSeriesId(), seriesEntity.getLabel(), status));
            }
            IResultSet iResultSet = new ResultSet(3050, vSeriesEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }


    @Override
    public void tree() {

    }

    @Override
    public void treeInversion() {
        String params = this.getPara("p");
        LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        SeriesEntity seriesEntity = iSeriesServices.retrieveById(vSeriesEntity.getSeriesId());
        LinkedList<TypeEntity> typeEntities = iTypeServices.retrievesInSeries(seriesEntity);
        for (TypeEntity typeEntity : typeEntities) {
            LinkedList<FormatEntity> formatEntities = iFormatServices.retrievesInType(typeEntity);
            for (FormatEntity formatEntity:formatEntities){
                VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                vTypeEntity.setParent(new VSeriesEntity(seriesEntity));
                vFormatEntity.setParent(vTypeEntity);
                vFormatEntities.add(vFormatEntity);
            }
        }
        IResultSet iResultSet = new ResultSet(3050, vFormatEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mCreate() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        String seriesId = UUID.randomUUID().toString();
        SeriesEntity seriesEntity = new SeriesEntity(seriesId, vSeriesEntity.getLabel());
        SeriesEntity result = iSeriesServices.mCreate(seriesEntity);
        if (result == null) {
            seriesEntity.setSeriesId(null);
            IResultSet iResultSet = new ResultSet(3000, vSeriesEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            vSeriesEntity.setSeriesId(result.getSeriesId());
            IResultSet iResultSet = new ResultSet(3050, vSeriesEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mUpdate() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        SeriesEntity seriesEntity = new SeriesEntity(vSeriesEntity.getSeriesId(), vSeriesEntity.getLabel());
        SeriesEntity result = iSeriesServices.mUpdate(seriesEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(3000, vSeriesEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, vSeriesEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        SeriesEntity seriesEntity = new SeriesEntity(vSeriesEntity.getSeriesId(), null, 0, vSeriesEntity.getStatus());
        SeriesEntity result = null;
        if (seriesEntity.getStatus() == 0){
            result = iSeriesServices.mBlock(seriesEntity);
        }else if (seriesEntity.getStatus() == 1){
            result = iSeriesServices.mUnBlock(seriesEntity);
        }
        if (result == null) {
            IResultSet iResultSet = new ResultSet(3000, vSeriesEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, vSeriesEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }


    @Override
    public void mRetrieve() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        LinkedList<SeriesEntity> seriesEntities = iSeriesServices.mRetrieves();
        if (seriesEntities == null) {
            IResultSet iResultSet = new ResultSet(3000, null, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<VSeriesEntity> vSeriesEntities = new LinkedList<>();
            for (SeriesEntity seriesEntity : seriesEntities) {
                String sessionId = vSeriesEntity == null ? null : vSeriesEntity.getSessionId();
                vSeriesEntities.add(new VSeriesEntity(sessionId, seriesEntity.getSeriesId(), seriesEntity.getLabel(), seriesEntity.getStatus()));
            }
            IResultSet iResultSet = new ResultSet(3050, vSeriesEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }




}
