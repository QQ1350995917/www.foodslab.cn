package cn.foodslab.user;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserController extends Controller implements IUserController {
    IUserServices userServices = new UserServices();
    @Override
    public void index() {

    }

    @Override
    public void retrieve() {
        IResultSet resultSet = userServices.retrieve();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String telephone = this.getPara("telephone");
        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID().toString(), telephone, null, 0, null, null, null, 0, UUID.randomUUID().toString());
        IResultSet resultSet = userServices.create(accountEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {
        String userId = this.getPara("userId");
        String accountId = this.getPara("accountId");
        String telephone = this.getPara("telephone");
        String name = this.getPara("name");
        String address = this.getPara("address");
        int gender = -1;
        if (isParaExists("gender")){
            gender = this.getParaToInt("gender");
        }
        String birthday = this.getPara("birthday");
        AccountEntity accountEntity = new AccountEntity(accountId, telephone, name, gender, address, null, birthday, 0, userId);
        IResultSet resultSet = userServices.update(accountEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void block() {

    }

    @Override
    public void bind() {

    }

}
