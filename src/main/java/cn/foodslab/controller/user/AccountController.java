package cn.foodslab.controller.user;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountController extends Controller implements IAccountController {
    IAccountServices userServices = new AccountServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {
        String phone = this.getPara("phone");
        AccountEntity retrieve = userServices.retrieve(phone);
        IResultSet resultSet = new ResultSet();
        if (retrieve != null) {
            resultSet.setCode(200);
            resultSet.setData(retrieve);
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("不存在该用户");
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String telephone = this.getPara("telephone");
        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID().toString(), telephone, null, 0, null, null, null, 0, UUID.randomUUID().toString());
        AccountEntity result = userServices.create(accountEntity);
        IResultSet resultSet = new ResultSet();
        if (result != null) {
            resultSet.setCode(200);
            resultSet.setData(result);
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("创建账户失败");
        }
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
        if (isParaExists("gender")) {
            gender = this.getParaToInt("gender");
        }
        String birthday = this.getPara("birthday");
        AccountEntity accountEntity = new AccountEntity(accountId, telephone, name, gender, address, null, birthday, 0, userId);
        AccountEntity result = userServices.update(accountEntity);
        IResultSet resultSet = new ResultSet();
        if (result != null) {
            resultSet.setCode(200);
            resultSet.setData(result);
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void block() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void exist() {

    }

    @Override
    public void password() {

    }

    @Override
    public void portrait() {

    }
}
