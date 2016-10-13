package cn.foodslab.controller.user;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.user.*;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountController extends Controller implements IAccountController {
    IAccountServices iAccountServices = new AccountServices();

    @Override
    public void index() {

    }

    @Override
    public void exist() {

    }

    @Override
    public void smsCode() {

    }

    @Override
    public void create() {
        String telephone = this.getPara("telephone");
        AccountEntity accountEntity = new AccountEntity(UUID.randomUUID().toString(), telephone, null, 0, null, null, null, 0, UUID.randomUUID().toString());
        AccountEntity result = iAccountServices.create(accountEntity);
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
    public void login() {
        String phone = this.getPara("phone");
        AccountEntity retrieve = iAccountServices.retrieveByIdentity(phone);
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
        AccountEntity result = iAccountServices.update(accountEntity);
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
    public void bind() {

    }

    @Override
    public void phone() {

    }

    @Override
    public void unBind() {

    }

    @Override
    public void password() {

    }

    @Override
    public void portrait() {

    }

    @Override
    public void retrieve() {

    }

    @Override
    public void mRetrieves() {
        LinkedList<UserEntity> userEntities = iAccountServices.mRetrieveUsers(1, 1);
        LinkedList<VUserEntity> vUserEntities = new LinkedList<>();
        for (UserEntity userEntity : userEntities) {
            VUserEntity vUserEntity = new VUserEntity(userEntity);
            LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(userEntity.getUserId());
            LinkedList<VAccountEntity> vAccountEntities = new LinkedList<>();
            for (AccountEntity accountEntity : accountEntities) {
                VAccountEntity result = new VAccountEntity(accountEntity);
                vAccountEntities.add(result);
            }
            vUserEntity.setChildren(vAccountEntities);
            vUserEntities.add(vUserEntity);
        }
        IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vUserEntities, "success");
        renderJson(JSON.toJSONString(iResultSet));
    }


    @Override
    public void mQueryUsers() {

    }

    @Override
    public void mMark() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        UserEntity userEntity = null;
        if (vUserEntity.getStatus() == 1) {
            userEntity = iAccountServices.mBlock(vUserEntity.getUserEntity());
        } else if (vUserEntity.getStatus() == 2) {
            userEntity = iAccountServices.mUnBlock(vUserEntity.getUserEntity());
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vUserEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        }
        if (userEntity == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vUserEntity, "fail");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), userEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Override
    public void mRetrieveAccounts() {

    }
}
