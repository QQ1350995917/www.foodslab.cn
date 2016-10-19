package cn.foodslab.controller.menu;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.manager.VManagerEntity;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.service.menu.IMenuServices;
import cn.foodslab.service.menu.MenuEntity;
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
@Before(ManagerInterceptor.class)
public class MenuController extends Controller implements IMenuController {
    private IMenuServices iMenuServices = new MenuServices();
    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VManagerEntity vManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(vManagerEntity);
        LinkedList<VMenuEntity> result = new LinkedList<>();
        for (MenuEntity menuEntity:menuEntities){
            VMenuEntity vMenuEntity = new VMenuEntity(menuEntity);
            result.add(vMenuEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }

    @Override
    public void mRetrieves() {
        String params = this.getPara("p");
        VMenuEntity vMenuEntity = JSON.parseObject(params, VMenuEntity.class);
        LinkedList<MenuEntity> menuEntities = iMenuServices.mRetrievesByAdmin(vMenuEntity.getCategory());
        LinkedList<VMenuEntity> result = new LinkedList<>();
        for (MenuEntity menuEntity:menuEntities){
            result.add(new VMenuEntity(menuEntity));
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), result, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }
}
