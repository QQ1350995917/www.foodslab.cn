package cn.foodslab.back.manager;

import cn.foodslab.back.common.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-30 10:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员业务逻辑流程控制器
 */
public class ManagerController extends Controller implements IManagerController{

    private IManagerServices iManagerServices = new ManagerServices();

    @Override
    public void index() {
        IResultSet resultSet = iManagerServices.retrieveManager();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void check() {

    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }
}
