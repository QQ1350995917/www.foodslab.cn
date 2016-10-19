package cn.foodslab.controller.receiver;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
@Before(SessionInterceptor.class)
public class ReceiverController extends Controller implements IReceiverController {

    private IAccountServices iAccountServices = new AccountServices();
    private IReceiverService iReceiverService = new ReceiverServices();

    @Clear(SessionInterceptor.class)
    @Override
    public void index() {

    }

    @Override
    public void retrieves() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        VUserEntity sessionUserEntity = SessionContext.getSessionUser(vUserEntity.getCs());
        LinkedList<VReceiverEntity> result = new LinkedList<>();
        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.retrieves(sessionUserEntity.getChildren());
        for (ReceiverEntity receiverEntity : receiverEntities) {
            result.add(new VReceiverEntity(receiverEntity));
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String params = this.getPara("p");
        VReceiverEntity vReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        vReceiverEntity.setStatus(2);
        VUserEntity sessionUser = SessionContext.getSessionUser(vReceiverEntity.getCs());
        String receiverId = UUID.randomUUID().toString();
        vReceiverEntity.setReceiverId(receiverId);
        ReceiverEntity receiverEntity = iReceiverService.create(sessionUser.getChildren().get(0),vReceiverEntity);
        if (receiverEntity == null) {
            vReceiverEntity.setReceiverId(null);
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vReceiverEntity);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            VReceiverEntity result = new VReceiverEntity(receiverEntity);
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void update() {
        String params = this.getPara("p");
        VReceiverEntity vReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        VUserEntity sessionUser = SessionContext.getSessionUser(vReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.updateById(sessionUser.getChildren(),vReceiverEntity);
        if (receiverEntity == null) {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vReceiverEntity);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            VReceiverEntity result = new VReceiverEntity(receiverEntity);
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(result);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void delete() {
        String params = this.getPara("p");
        VReceiverEntity vReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        VUserEntity sessionUser = SessionContext.getSessionUser(vReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.deleteById(sessionUser.getChildren(),vReceiverEntity.getReceiverId());
        if (receiverEntity == null) {
            IResultSet resultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vReceiverEntity);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            IResultSet resultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            resultSet.setData(vReceiverEntity);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void king() {
        String params = this.getPara("p");
        VReceiverEntity vReceiverEntity = JSON.parseObject(params, VReceiverEntity.class);
        VUserEntity sessionUser = SessionContext.getSessionUser(vReceiverEntity.getCs());
        ReceiverEntity receiverEntity = iReceiverService.kingReceiverInUser(sessionUser.getChildren(), vReceiverEntity);
        if (receiverEntity == null) {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(new VReceiverEntity(vReceiverEntity));
            renderJson(JSON.toJSONString(resultSet));
        } else {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
            VReceiverEntity result = new VReceiverEntity(receiverEntity);
            resultSet.setData(result);
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Before(ManagerInterceptor.class)
    @Override
    public void mRetrieveByUser() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        LinkedList<AccountEntity> accountEntities = iAccountServices.mRetrieveByUserId(vUserEntity.getUserId());
        String[] accountIds = new String[accountEntities.size()];
        for (int index = 0; index < accountEntities.size(); index++) {
            accountIds[index] = accountEntities.get(index).getAccountId();
        }
        LinkedList<VReceiverEntity> result = new LinkedList<>();
        LinkedList<ReceiverEntity> receiverEntities = iReceiverService.mRetrieves(accountIds);
        for (ReceiverEntity receiverEntity : receiverEntities) {
            result.add(new VReceiverEntity(receiverEntity));
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setData(result);
        renderJson(JSON.toJSONString(resultSet));
    }
}
