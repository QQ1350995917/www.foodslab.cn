package cn.foodslab.front.user;

import cn.foodslab.back.common.IResultSet;
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
    public void createAccount() {
        String telephone = this.getPara("telephone");
        String password = this.getPara("password");
        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID().toString(), telephone, password, null, 0, null, null, null, 0, UUID.randomUUID().toString());
        IResultSet resultSet = userServices.createAccount(accountEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void updateAccount() {
        String userId = this.getPara("userId");
        String accountId = this.getPara("accountId");
        String telephone = this.getPara("telephone");
        String password = this.getPara("password");
        String name = this.getPara("name");
        String address = this.getPara("address");
        int gender = -1;
        if (isParaExists("gender")){
            gender = this.getParaToInt("gender");
        }
        String birthday = this.getPara("birthday");
        AccountEntity accountEntity = new AccountEntity(accountId, telephone, password, name, gender, address, null, birthday, 0, userId);
        IResultSet resultSet = userServices.updateAccount(accountEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void bindAccount() {

    }

    @Override
    public void createReceiver() {

    }

    @Override
    public void updateReceiver() {

    }

    @Override
    public void retrieveAccount() {

    }

    @Override
    public void retrieveReceiver() {

    }
}
