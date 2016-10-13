package cn.foodslab.controller.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-09-22 17:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeController extends Controller implements ITypeController {
    private ISeriesServices iSeriesServices = new SeriesServices();
    private ITypeServices iTypeServices = new TypeServices();
    private IFormatServices iFormatServices = new FormatServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {

    }

    @Override
    public void mCreate() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        String typeId = UUID.randomUUID().toString();
        TypeEntity typeEntity = new TypeEntity(vTypeEntity.getSeriesId(), typeId, vTypeEntity.getLabel());
        typeEntity.setStatus(1);
        boolean exist = iTypeServices.mExistInSeries(typeEntity.getLabel(), typeEntity.getSeriesId());
        if (exist) {
            vTypeEntity.setTypeId(null);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vTypeEntity);
            iResultSet.setMessage("已经存在同名类型");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            TypeEntity result = iTypeServices.mCreate(typeEntity);
            if (result == null) {
                vTypeEntity.setTypeId(null);
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vTypeEntity, "fail");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                vTypeEntity.setTypeId(typeEntity.getTypeId());
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vTypeEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

    /**
     * 更新产品类型名称
     * 在同一个系列下不能出现相同的类型名称
     */
    @Override
    public void mUpdate() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setSeriesId(vTypeEntity.getSeriesId());
        typeEntity.setTypeId(vTypeEntity.getTypeId());
        typeEntity.setLabel(vTypeEntity.getLabel());
        boolean exist = iTypeServices.mExistInSeries(typeEntity.getLabel(), typeEntity.getSeriesId());
        if (exist) {
            vTypeEntity.setTypeId(null);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vTypeEntity);
            iResultSet.setMessage("已经存在同名类型");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            TypeEntity result = iTypeServices.mUpdate(typeEntity);
            if (result == null) {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vTypeEntity, "fail");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vTypeEntity, "success");
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTypeId(vTypeEntity.getTypeId());
        typeEntity.setStatus(vTypeEntity.getStatus());
        TypeEntity result = null;
        if (typeEntity.getStatus() == -1) {
            result = iTypeServices.mDelete(typeEntity);
        } else if (typeEntity.getStatus() == 1) {
            result = iTypeServices.mBlock(typeEntity);
        } else if (typeEntity.getStatus() == 2) {
            result = iTypeServices.mUnBlock(typeEntity);
        }

        if (result == null) {
            IResultSet iResultSet = new ResultSet(3000, vTypeEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, vTypeEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mImage() {

    }

    @Override
    public void mImageDelete() {

    }

    @Override
    public void mSummary() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTypeId(vTypeEntity.getTypeId());
        typeEntity.setSummary(vTypeEntity.getSummary());
        TypeEntity result = iTypeServices.mUpdateSummary(typeEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(3000, vTypeEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, vTypeEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mDirections() {
        String params = this.getPara("p");
        VTypeEntity vTypeEntity = JSON.parseObject(params, VTypeEntity.class);
        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setTypeId(vTypeEntity.getTypeId());
        typeEntity.setDirections(vTypeEntity.getDirections());
        TypeEntity result = iTypeServices.mUpdateDirections(typeEntity);
        if (result == null) {
            IResultSet iResultSet = new ResultSet(3000, vTypeEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(3050, vTypeEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mRetrieves() {
        String params = this.getPara("p");
        VSeriesEntity vSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        SeriesEntity seriesEntity = new SeriesEntity(vSeriesEntity.getSeriesId(), vSeriesEntity.getLabel());
        LinkedList<TypeEntity> typeEntities = iTypeServices.mRetrievesInSeries(seriesEntity);
        LinkedList<VTypeEntity> vTypeEntities = new LinkedList<>();
        for (TypeEntity typeEntity : typeEntities) {
            vTypeEntities.add(new VTypeEntity(typeEntity));
        }
        IResultSet iResultSet = new ResultSet(3050, vTypeEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mRetrieve() {

    }
}
