package cn.foodslab.controller.receiver;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.service.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */

public class ReceiverController extends Controller implements IReceiverController {

    private IAccountServices iAccountServices = new AccountServices();
    private IReceiverService iReceiverService = new ReceiverServices();

    @Override
    public void index() {

    }

    @Override
    public void retrieve() {
        String accountId = getPara("accountId");
        UserEntity userEntity = iAccountServices.retrieveUserByAccountId(accountId);
        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.retrieveByUserId(userEntity.getUserId());
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(receiverEntities);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String accountId = getPara("accountId");
        String receiverId = UUID.randomUUID().toString();
        String name = getPara("name");
        String phone0 = getPara("phone0");
        String phone1 = getPara("phone1");
        String province = getPara("province");
        String city = getPara("city");
        String county = getPara("county");
        String town = getPara("town");
        String village = getPara("village");
        String append = getPara("append");
        ReceiverEntity receiverEntity = new ReceiverEntity(receiverId, province, city, county, town, village, append, name, phone0, phone1, 1, accountId);
        ReceiverEntity receiverEntityResult = iReceiverService.create(receiverEntity);
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(receiverEntityResult);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {
        String accountId = getPara("accountId");
        String receiverId = getPara("receiverId");
        String name = getPara("name");
        String phone0 = getPara("phone0");
        String phone1 = getPara("phone1");
        String province = getPara("province");
        String city = getPara("city");
        String county = getPara("county");
        String town = getPara("town");
        String village = getPara("village");
        String append = getPara("append");
        int status = Integer.parseInt(getPara("status"));

        ReceiverEntity receiverEntity = new ReceiverEntity(receiverId, province, city, county, town, village, append, name, phone0, phone1, status, accountId);
        ReceiverEntity update = iReceiverService.updateById(receiverEntity);
        if (update != null) {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(update);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void delete() {
        String accountId = getPara("accountId");
        String receiverId = getPara("receiverId");
        if (accountId != null) {
            ReceiverEntity receiverEntity = iReceiverService.deleteById(receiverId);
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(receiverEntity);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void king() {

    }

    @Override
    public void mRetrieveByUser() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.mRetrieveByUserId(vUserEntity.getUserEntity().getUserId());
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(receiverEntities);
        renderJson(JSON.toJSONString(resultSet));
    }
}
