package cn.foodslab.controller.manager;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.menu.VMenuEntity;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.manager.IManagerServices;
import cn.foodslab.service.manager.ManagerEntity;
import cn.foodslab.service.manager.ManagerServices;
import cn.foodslab.service.menu.IMenuServices;
import cn.foodslab.service.menu.MenuEntity;
import cn.foodslab.service.menu.MenuServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-07-30 10:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员业务逻辑流程控制器
 */
public class ManagerController extends Controller implements IManagerController {
    private IManagerServices iManagerServices = new ManagerServices();
    private IMenuServices iMenuServices = new MenuServices();

    @Override
    public void index() {

    }

    @Override
    public void mLogin() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = iManagerServices.mRetrieve(vManagerEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (managerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
            iResultSet.setMessage("用户名或密码错误");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            LinkedList<VMenuEntity> vMenuEntities = new LinkedList<>();
            LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(managerEntity);
            for (MenuEntity menuEntity : menuEntities) {
                VMenuEntity vMenuEntity = new VMenuEntity(menuEntity);
                vMenuEntities.add(vMenuEntity);
            }
            HttpSession session = this.getSession(true);
            String sessionId = session.getId();
            vManagerEntity.setManagerId(managerEntity.getManagerId());
            vManagerEntity.setLoginName(managerEntity.getLoginName());
            vManagerEntity.setUsername(managerEntity.getUsername());
            vManagerEntity.setPassword(managerEntity.getPassword());
            vManagerEntity.setMenus(vMenuEntities);
            vManagerEntity.setCs(sessionId);
            session.setAttribute(SessionContext.KEY_USER, vManagerEntity);

            iResultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            iResultSet.setData(new VManagerEntity(sessionId));
            iResultSet.setMessage("登录成功");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mExit() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        SessionContext.delSession(vManagerEntity.getCs());
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setMessage("成功退出");
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mRetrieve() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        VManagerEntity sessionManager = SessionContext.getSessionManager(vManagerEntity.getCs());
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (sessionManager == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            iResultSet.setData(sessionManager);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void mUpdate() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = iManagerServices.mUpdate(vManagerEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (managerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            VManagerEntity result = new VManagerEntity(managerEntity);
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void MRetrieves() {
        LinkedList<VManagerEntity> vManagerEntities = new LinkedList<>();
        LinkedList<ManagerEntity> managerEntities = iManagerServices.MRetrieves();
        for (ManagerEntity managerEntity : managerEntities) {
            VManagerEntity vManagerEntity = new VManagerEntity(managerEntity);
            LinkedList<VMenuEntity> vMenuEntities = new LinkedList<>();
            LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(managerEntity);
            for (MenuEntity menuEntity : menuEntities) {
                vMenuEntities.add(new VMenuEntity(menuEntity));
            }
            vManagerEntity.setMenus(vMenuEntities);
            vManagerEntities.add(vManagerEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        iResultSet.setData(vManagerEntities);
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void MCreate() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        vManagerEntity.setManagerId(UUID.randomUUID().toString());
        vManagerEntity.setLevel(1);
        vManagerEntity.setStatus(1);
        ManagerEntity managerEntity = iManagerServices.MCreate(vManagerEntity, vManagerEntity.getMenus());
        VManagerEntity result = new VManagerEntity(managerEntity);
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (result == null) {
            vManagerEntity.setManagerId(null);
            vManagerEntity.setLevel(0);
            vManagerEntity.setStatus(0);
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
        } else {
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void MUpdate() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = iManagerServices.MUpdate(vManagerEntity, vManagerEntity.getMenus());
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        if (managerEntity == null) {
            iResultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            iResultSet.setData(vManagerEntity);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            VManagerEntity result = new VManagerEntity(managerEntity);
            iResultSet.setData(result);
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void MMark() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        ManagerEntity managerEntity = null;
        if (vManagerEntity.getStatus() == 1) {
            managerEntity = iManagerServices.MBlock(vManagerEntity);
        } else if (vManagerEntity.getStatus() == 2) {
            managerEntity = iManagerServices.MUnBlock(vManagerEntity);
        } else if (vManagerEntity.getStatus() == -1) {
            managerEntity = iManagerServices.MDelete(vManagerEntity);
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
