package cn.foodslab.back.users;

import cn.foodslab.back.common.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-08-16 13:46.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UsersController extends Controller implements IUsersController{

    IUsersServices usersServices = new UsersServices();

    @Override
    public void index() {
        IResultSet result = usersServices.retrieve();
        renderJson(JSON.toJSONString(result));
    }
}
