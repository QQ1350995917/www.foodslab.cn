package cn.foodslab.controller.menu;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.manager.VManagerEntity;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.menu.IMenuServices;
import cn.foodslab.service.menu.MenuServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */

public class MenuController extends Controller implements IMenuController {
    private IMenuServices iMenuServices = new MenuServices();
    @Override
    public void index() {

    }

    @Override
    @Before({SessionInterceptor.class})
    public void mRetrieves() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        LinkedList<VMenuEntity> vMenuEntities = SessionContext.getSessionManager(vManagerEntity.getCs()).getMenus();
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vMenuEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }
}
