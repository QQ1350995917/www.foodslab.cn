package cn.foodslab.controller.product;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.MenuInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.product.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jfinal.aop.Before;
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
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVSeriesEntity.getSeriesId() == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId")));
            return;
        }

        SeriesEntity seriesEntity = iSeriesServices.retrieveById(requestVSeriesEntity.getSeriesId());
        if (seriesEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(new VSeriesEntity(seriesEntity));
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId","label")));

    }

    @Override
    public void retrieves() {
        LinkedList<SeriesEntity> seriesEntities = iSeriesServices.retrieves();
        IResultSet iResultSet = new ResultSet();
        if (seriesEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VSeriesEntity> responseVSeriesEntities = new LinkedList<>();
        for (SeriesEntity seriesEntity : seriesEntities) {
            responseVSeriesEntities.add(new VSeriesEntity(seriesEntity));
        }
        if (responseVSeriesEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVSeriesEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label", "queue")));
    }

    @Override
    public void tree() {

    }

    @Override
    public void treeInversion() {
        String params = this.getPara("p");
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (requestVSeriesEntity.getSeriesId() == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId")));
            return;
        }

        LinkedList<VFormatEntity> responseVFormatEntities = new LinkedList<>();
        SeriesEntity seriesEntity = iSeriesServices.retrieveById(requestVSeriesEntity.getSeriesId());
        if (seriesEntity != null) {
            LinkedList<TypeEntity> typeEntities = iTypeServices.retrievesInSeries(seriesEntity);
            if (typeEntities != null) {
                for (TypeEntity typeEntity : typeEntities) {
                    LinkedList<FormatEntity> formatEntities = iFormatServices.retrievesInType(typeEntity);
                    if (formatEntities != null) {
                        for (FormatEntity formatEntity : formatEntities) {
                            VFormatEntity vFormatEntity = new VFormatEntity(formatEntity);
                            VTypeEntity vTypeEntity = new VTypeEntity(typeEntity);
                            vTypeEntity.setParent(new VSeriesEntity(seriesEntity));
                            vFormatEntity.setParent(vTypeEntity);
                            responseVFormatEntities.add(vFormatEntity);
                        }
                    }
                }
            }
        }
        if (responseVFormatEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVFormatEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SerializeFilter[]{
                new SimplePropertyPreFilter(VFormatEntity.class, "formatId", "label", "meta", "price", "priceMeta", "pricing", "postage", "postageMeta", "typeId", "parent"),
                new SimplePropertyPreFilter(VTypeEntity.class, "typeId", "label", "seriesId", "parent"),
                new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")
        }));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVSeriesEntity.checkCreateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "label")));
            return;
        }

        boolean mExist = iSeriesServices.mExist(requestVSeriesEntity.getLabel());
        if (mExist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "label")));
            return;
        }

        String seriesId = UUID.randomUUID().toString();
        requestVSeriesEntity.setSeriesId(seriesId);
        requestVSeriesEntity.setStatus(1);
        requestVSeriesEntity.setQueue(0);
        SeriesEntity result = iSeriesServices.mCreate(requestVSeriesEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "label")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVSeriesEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label", "status", "queue")));

    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVSeriesEntity.checkUpdateParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")));
            return;
        }

        boolean mExist = iSeriesServices.mExist(requestVSeriesEntity.getLabel());
        if (mExist) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_REPEAT.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_CANNOT_REPEAT);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")));
            return;
        }

        SeriesEntity result = iSeriesServices.mUpdate(requestVSeriesEntity);
        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVSeriesEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VSeriesEntity requestVSeriesEntity = JSON.parseObject(params, VSeriesEntity.class);
        IResultSet iResultSet = new ResultSet();
        if (!requestVSeriesEntity.checkMarkParams()) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "status")));
            return;
        }
        SeriesEntity result = null;
        if (requestVSeriesEntity.getStatus() == 1) {
            result = iSeriesServices.mBlock(requestVSeriesEntity);
        } else if (requestVSeriesEntity.getStatus() == 2) {
            result = iSeriesServices.mUnBlock(requestVSeriesEntity);
        } else if (requestVSeriesEntity.getStatus() == -1) {
            result = iSeriesServices.mDelete(requestVSeriesEntity);
        }

        if (result == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVSeriesEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "status")));
            return;
        }

        iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        iResultSet.setData(requestVSeriesEntity);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "status")));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    public void mRetrieves() {
        LinkedList<SeriesEntity> seriesEntities = iSeriesServices.mRetrieves();
        IResultSet iResultSet = new ResultSet();
        if (seriesEntities == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "status")));
            return;
        }

        LinkedList<VSeriesEntity> responseVSeriesEntities = new LinkedList<>();
        for (SeriesEntity seriesEntity : seriesEntities) {
            responseVSeriesEntities.add(new VSeriesEntity(seriesEntity));
        }
        if (responseVSeriesEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVSeriesEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet, new SimplePropertyPreFilter(VSeriesEntity.class, "seriesId", "label", "status", "queue")));
    }
}
