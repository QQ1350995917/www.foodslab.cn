package cn.foodslab.controller.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-22 17:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FormatController extends Controller implements IFormatController {
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieves() {

    }

    @Override
    public void retrieve() {

    }

    @Override
    public void retrieveTree() {

    }

    @Override
    public void retrieveTreeInversion() {
        String params = this.getPara("p");
        VFormatEntity vFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        FormatEntity formatEntity = iFormatServices.retrieveById(vFormatEntity.getFormatId());
        if (formatEntity == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            TypeEntity typeEntity = iTypeServices.retrieveById(formatEntity.getTypeId());
            SeriesEntity seriesEntity = iSeriesServices.retrieveById(typeEntity.getSeriesId());
            VFormatEntity result = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
            vTypeEntity.setParent(new VSeriesEntity(seriesEntity));
            result.setParent(vTypeEntity);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void recommends() {
        LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
        LinkedList<FormatEntity> formatEntities = iFormatServices.mRetrieveByWeight(0, 0);
        for (FormatEntity formatEntity : formatEntities) {
            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(formatEntity.getTypeEntity());
            VSeriesEntity vSeriesEntity = new VSeriesEntity(formatEntity.getTypeEntity().getSeriesEntity());
            vTypeEntity.setParent(vSeriesEntity);
            vFormatEntity.setParent(vTypeEntity);
            vFormatEntities.add(vFormatEntity);
        }

        if (formatEntities == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntities, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vFormatEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mCreate() {
        String params = this.getPara("p");
        VFormatEntity vFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        FormatEntity formatEntity = vFormatEntity.getFormatEntity();
        String formatId = UUID.randomUUID().toString();
        formatEntity.setFormatId(formatId);
        formatEntity.setStatus(1);
        if (iFormatServices.mExist(formatEntity)) {
            vFormatEntity.setFormatId(null);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            FormatEntity result = iFormatServices.mCreate(formatEntity);
            if (result == null) {
                vFormatEntity.setFormatId(null);
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vFormatEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

    @Override
    public void mUpdate() {
        String params = this.getPara("p");
        VFormatEntity vFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        FormatEntity formatEntity = vFormatEntity.getFormatEntity();
        if (iFormatServices.mExist(formatEntity)) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            FormatEntity result = iFormatServices.mUpdate(formatEntity);
            if (result == null) {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VFormatEntity vFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setFormatId(vFormatEntity.getFormatId());
        formatEntity.setStatus(vFormatEntity.getStatus());
        FormatEntity result = null;
        if (formatEntity.getStatus() == -1) {
            result = iFormatServices.mDelete(formatEntity);
        } else if (formatEntity.getStatus() == 1) {
            result = iFormatServices.mBlock(formatEntity);
        } else if (formatEntity.getStatus() == 2) {
            result = iFormatServices.mUnBlock(formatEntity);
        }

        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }


    @Override
    public void mKingWeight() {
        String params = this.getPara("p");
        VFormatEntity vFormatEntity = JSON.parseObject(params, VFormatEntity.class);
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setFormatId(vFormatEntity.getFormatId());
        formatEntity.setWeight(vFormatEntity.getWeight());
        FormatEntity result = iFormatServices.mKingWeight(formatEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mSwapWeight() {
        String params = this.getPara("p");
        Map paramsMap = JSON.parseObject(params, Map.class);

        String formatId1 = paramsMap.get("formatId1").toString();
        int weight1 = Integer.parseInt(paramsMap.get("weight1").toString());
        FormatEntity formatEntity1 = new FormatEntity();
        formatEntity1.setFormatId(formatId1);
        formatEntity1.setWeight(weight1);

        String formatId2 = paramsMap.get("formatId2").toString();
        int weight2 = Integer.parseInt(paramsMap.get("weight2").toString());
        FormatEntity formatEntity2 = new FormatEntity();
        formatEntity2.setFormatId(formatId2);
        formatEntity2.setWeight(weight2);

        FormatEntity[] result = iFormatServices.mSwapWeight(formatEntity1, formatEntity2);

        if (result == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), paramsMap, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mWeights() {
        LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
        LinkedList<FormatEntity> formatEntities = iFormatServices.mRetrieveByWeight(0, 0);
        for (FormatEntity formatEntity : formatEntities) {
            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
            VTypeEntity vTypeEntity = new VTypeEntity(formatEntity.getTypeEntity());
            VSeriesEntity vSeriesEntity = new VSeriesEntity(formatEntity.getTypeEntity().getSeriesEntity());
            vTypeEntity.setParent(vSeriesEntity);
            vFormatEntity.setParent(vTypeEntity);
            vFormatEntities.add(vFormatEntity);
        }

        if (formatEntities == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vFormatEntities, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vFormatEntities, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }


    @Override
    public void mRetrieves() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        TypeEntity typeEntity = new TypeEntity(vTypeEntity.getSeriesId(), vTypeEntity.getTypeId(), vTypeEntity.getLabel());
        LinkedList<FormatEntity> formatEntities = iFormatServices.mRetrievesInType(typeEntity);
        LinkedList<VFormatEntity> vFormatEntities = new LinkedList<>();
        for (FormatEntity formatEntity : formatEntities) {
            vFormatEntities.add(new VFormatEntity(formatEntity));
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vFormatEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mRetrieve() {

    }
}
