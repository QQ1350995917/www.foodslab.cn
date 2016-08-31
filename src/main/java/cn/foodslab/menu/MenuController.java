package cn.foodslab.menu;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-30 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class MenuController extends Controller implements IMenuController{
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
