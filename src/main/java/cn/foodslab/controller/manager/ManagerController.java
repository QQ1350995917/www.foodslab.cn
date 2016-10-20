package cn.foodslab.controller.manager;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.service.manager.IManagerServices;
import cn.foodslab.service.manager.ManagerEntity;
import cn.foodslab.service.manager.ManagerServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-07-30 10:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员业务逻辑流程控制器
 */
@Before(ManagerInterceptor.class)
public class ManagerController extends Controller implements IManagerController {

    private IManagerServices iManagerServices = new ManagerServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {

    }

    @Override
    public void password() {

    }

    @Override
    public void mRetrieves() {
        LinkedList<VManagerEntity> vManagerEntities = new LinkedList<>();
        LinkedList<ManagerEntity> managerEntities = iManagerServices.mRetrieves();
        for (ManagerEntity managerEntity : managerEntities) {
            vManagerEntities.add(new VManagerEntity(managerEntity));
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(vManagerEntities);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mCreate() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        vManagerEntity.setManagerId(UUID.randomUUID().toString());
        vManagerEntity.setLevel(1);
        vManagerEntity.setStatus(1);
        ManagerEntity managerEntity = iManagerServices.mCreate(vManagerEntity);
        VManagerEntity result = new VManagerEntity(managerEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (result == null) {
            vManagerEntity.setManagerId(null);
            vManagerEntity.setLevel(0);
            vManagerEntity.setStatus(0);
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
        } else {
            if (vManagerEntity.getMenus() != null) {

            } else {

            }
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mUpdate() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = iManagerServices.mUpdate(vManagerEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (managerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
        } else {
            VManagerEntity result = new VManagerEntity(managerEntity);
            if (vManagerEntity.getMenus() != null) {

            } else {

            }
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = null;
        if (vManagerEntity.getStatus() == 1) {
            managerEntity = iManagerServices.mBlock(vManagerEntity);
        } else if (vManagerEntity.getStatus() == 2) {
            managerEntity = iManagerServices.mUnBlock(vManagerEntity);
        } else if (vManagerEntity.getStatus() == -1) {
            managerEntity = iManagerServices.mDelete(vManagerEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (managerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
        } else {
            VManagerEntity result = new VManagerEntity(managerEntity);
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }
}
