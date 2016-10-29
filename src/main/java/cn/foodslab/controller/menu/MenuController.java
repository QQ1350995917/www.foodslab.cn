package cn.foodslab.controller.menu;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.link.VLinkEntity;
import cn.foodslab.controller.manager.VManagerEntity;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.menu.IMenuServices;
import cn.foodslab.service.menu.MenuEntity;
import cn.foodslab.service.menu.MenuServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
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
        VManagerEntity requestVManagerEntity = JSON.parseObject(params, VManagerEntity.class);
        IResultSet iResultSet = new ResultSet();
        VManagerEntity sessionManager = SessionContext.getSessionManager(requestVManagerEntity.getCs());
        LinkedList<MenuEntity> menuEntities = iMenuServices.retrievesByManager(sessionManager);
        if (menuEntities == null){
            iResultSet.setCode(IResultSet.ResultCode.RC_SEVER_ERROR.getCode());
            iResultSet.setData(requestVManagerEntity);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_ERROR);
            renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        LinkedList<VMenuEntity> responseVMenuEntities = new LinkedList<>();
        for (MenuEntity menuEntity:menuEntities){
            responseVMenuEntities.add(new VMenuEntity(menuEntity));
        }
        if (responseVMenuEntities.size() == 0) {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS_EMPTY.getCode());
        } else {
            iResultSet.setCode(IResultSet.ResultCode.RC_SUCCESS.getCode());
        }
        iResultSet.setData(responseVMenuEntities);
        iResultSet.setMessage(IResultSet.ResultMessage.RM_SERVER_OK);
        renderJson(JSON.toJSONString(iResultSet,new SimplePropertyPreFilter(VMenuEntity.class,"menuId","label","flag","category")));
    }
}
