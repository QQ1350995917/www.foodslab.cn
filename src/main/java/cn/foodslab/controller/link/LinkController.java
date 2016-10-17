package cn.foodslab.controller.link;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.service.link.ILinkServices;
import cn.foodslab.service.link.LinkEntity;
import cn.foodslab.service.link.LinkServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
@Before(ManagerInterceptor.class)
public class LinkController extends Controller implements ILinkController {

    ILinkServices iLinkServices = new LinkServices();

    @Clear(ManagerInterceptor.class)
    @Override
    public void index() {

    }

    @Clear(ManagerInterceptor.class)
    @Override
    public void retrieves() {
        LinkedList<VLinkEntity> result = new LinkedList<>();
        LinkedList<LinkEntity> linkEntities = iLinkServices.retrieves();
        for (LinkEntity linkEntity : linkEntities) {
            VLinkEntity vLinkEntity = new VLinkEntity(linkEntity);
            LinkedList<VLinkEntity> childrenVlinkEntities = new LinkedList<>();
            LinkedList<LinkEntity> children = iLinkServices.retrievesByPid(linkEntity.getLinkId());
            for (LinkEntity child : children) {
                childrenVlinkEntities.add(new VLinkEntity(child));
            }
            vLinkEntity.setChildren(childrenVlinkEntities);
            result.add(vLinkEntity);
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void mCreate() {
        String params = this.getPara("p");
        VLinkEntity vLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        vLinkEntity.setStatus(1);
        if (vLinkEntity.getPid() == null) {
            String linkId = UUID.randomUUID().toString();
            vLinkEntity.setLinkId(linkId);
            vLinkEntity.setPid(linkId);
        } else {
            String linkId = UUID.randomUUID().toString();
            vLinkEntity.setLinkId(linkId);
        }
        IResultSet resultSet = new ResultSet();
        if (iLinkServices.mExist(vLinkEntity)) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vLinkEntity);
        } else {
            LinkEntity linkEntity = iLinkServices.mCreate(vLinkEntity);
            if (linkEntity == null) {
                resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
                resultSet.setData(vLinkEntity);
            } else {
                resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
                resultSet.setData(linkEntity);
            }
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void mUpdate() {
        String params = this.getPara("p");
        VLinkEntity vLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        IResultSet resultSet = new ResultSet();
        if (iLinkServices.mExist(vLinkEntity)) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vLinkEntity);
        } else {
            LinkEntity result = iLinkServices.mUpdate(vLinkEntity);
            if (result == null) {
                resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
                resultSet.setData(vLinkEntity);
            } else {
                resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
                resultSet.setData(result);
            }
        }
        renderJson(JSON.toJSONString(resultSet));

    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VLinkEntity vLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        LinkEntity result = null;
        if (vLinkEntity.getStatus() == -1) {
            result = iLinkServices.mDelete(vLinkEntity);
        } else if (vLinkEntity.getStatus() == 1) {
            result = iLinkServices.mBlock(vLinkEntity);
        } else if (vLinkEntity.getStatus() == 2) {
            result = iLinkServices.mUnBlock(vLinkEntity);
        }

        IResultSet resultSet = new ResultSet();
        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vLinkEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void mSwap() {
        String params = this.getPara("p");
        VLinkEntity vLinkEntity = JSON.parseObject(params, VLinkEntity.class);
        LinkEntity linkEntity1 = new LinkEntity(vLinkEntity.getLinkId1(), vLinkEntity.getWeight1());
        LinkEntity linkEntity2 = new LinkEntity(vLinkEntity.getLinkId2(), vLinkEntity.getWeight2());
        LinkEntity[] result = iLinkServices.mSwap(linkEntity1, linkEntity2);

        IResultSet resultSet = new ResultSet();

        if (result == null) {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vLinkEntity);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void mRetrieves() {
        String params = this.getPara("p");
        VLinkEntity requestVlinkEntity = JSON.parseObject(params, VLinkEntity.class);

        LinkedList<VLinkEntity> result = new LinkedList<>();
        LinkedList<LinkEntity> linkEntities = null;
        if (requestVlinkEntity.getLinkId() != null && requestVlinkEntity.getPid() != null && requestVlinkEntity.getLinkId().equals(requestVlinkEntity.getPid())) {
            linkEntities = iLinkServices.mRetrievesByPid(requestVlinkEntity.getLinkId());
        } else {
            linkEntities = iLinkServices.mRetrieves();
        }

        IResultSet resultSet = new ResultSet();
        if (linkEntities != null) {
            for (LinkEntity linkEntity : linkEntities) {
                result.add(new VLinkEntity(linkEntity));
            }
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(requestVlinkEntity);
        }
        renderJson(JSON.toJSONString(resultSet));
    }
}
