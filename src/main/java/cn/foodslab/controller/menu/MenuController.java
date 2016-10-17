package cn.foodslab.controller.menu;

import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.service.menu.IMenuServices;
import cn.foodslab.service.menu.MenuServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

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
        renderJson(JSON.toJSONString(iMenuServices.retrieveMenusByLevel(0)));
    }

    @Override
    public void status() {
        renderJson(JSON.toJSONString(iMenuServices.retrieveMenusByStatus(1)));
    }
}
