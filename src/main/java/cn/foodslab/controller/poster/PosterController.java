package cn.foodslab.controller.poster;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.poster.IPosterServices;
import cn.foodslab.service.poster.PosterEntity;
import cn.foodslab.service.poster.PosterServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterController extends Controller implements IPosterController {

    IPosterServices iPosterServices = new PosterServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
        LinkedList<VPosterEntity> result = new LinkedList<>();
        LinkedList<PosterEntity> posterEntities = iPosterServices.retrieves();
        for (PosterEntity posterEntity : posterEntities) {
            VPosterEntity vPosterEntity = new VPosterEntity(posterEntity);
            result.add(vPosterEntity);
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class,ManagerInterceptor.class})
    public void mCreate() {
        String params = this.getPara("p");
        VPosterEntity vPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        String posterId = UUID.randomUUID().toString();
        vPosterEntity.setPosterId(posterId);
        vPosterEntity.setStatus(1);
        PosterEntity result = iPosterServices.mCreate(vPosterEntity);
        IResultSet resultSet = new ResultSet();
        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            vPosterEntity.setPosterId(null);
            resultSet.setData(vPosterEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class,ManagerInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VPosterEntity vPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        PosterEntity result = iPosterServices.mUpdate(vPosterEntity);
        IResultSet resultSet = new ResultSet();
        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            vPosterEntity.setPosterId(null);
            resultSet.setData(vPosterEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class,ManagerInterceptor.class})
    public void mMark() {
        String params = this.getPara("p");
        VPosterEntity vPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        PosterEntity result = null;
        if (vPosterEntity.getStatus() == -1) {
            result = iPosterServices.mDelete(vPosterEntity);
        } else if (vPosterEntity.getStatus() == 1) {
            result = iPosterServices.mBlock(vPosterEntity);
        } else if (vPosterEntity.getStatus() == 2) {
            result = iPosterServices.mUnBlock(vPosterEntity);
        }
        IResultSet resultSet = new ResultSet();
        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vPosterEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class,ManagerInterceptor.class})
    public void mSwap() {
        String params = this.getPara("p");
        VPosterEntity vPosterEntity = JSON.parseObject(params, VPosterEntity.class);
        PosterEntity posterEntity1 = new PosterEntity(vPosterEntity.getPosterId1(), vPosterEntity.getWeight1());
        PosterEntity posterEntity2 = new PosterEntity(vPosterEntity.getPosterId2(), vPosterEntity.getWeight2());
        PosterEntity[] result = iPosterServices.mSwap(posterEntity1, posterEntity2);
        IResultSet resultSet = new ResultSet();
        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vPosterEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class,ManagerInterceptor.class})
    public void mRetrieves() {
        LinkedList<VPosterEntity> result = new LinkedList<>();
        LinkedList<PosterEntity> posterEntities = iPosterServices.mRetrieves();
        for (PosterEntity posterEntity : posterEntities) {
            VPosterEntity vPosterEntity = new VPosterEntity(posterEntity);
            result.add(vPosterEntity);
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }
}
