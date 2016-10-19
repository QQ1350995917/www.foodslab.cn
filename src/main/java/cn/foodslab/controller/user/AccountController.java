package cn.foodslab.controller.user;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import cn.foodslab.service.user.AccountEntity;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.service.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
@Before(SessionInterceptor.class)
public class AccountController extends Controller implements IAccountController {
    IAccountServices iAccountServices = new AccountServices();

    @Clear(SessionInterceptor.class)
    @Override
    public void index() {

    }

    @Override
    public void exist() {

    }

    @Clear(SessionInterceptor.class)
    @Override
    public void smsCode() {

    }

    @Clear(SessionInterceptor.class)
    @Override
    public void create() {
        String params = this.getPara("p");
        VAccountEntity vAccountEntity = JSON.parseObject(params, VAccountEntity.class);
        IResultSet resultSet = new ResultSet();
        if (vAccountEntity.getIdentity() != null && vAccountEntity.getPassword() != null) {
            boolean existAccount = iAccountServices.existAccount(vAccountEntity.getIdentity());
            if (existAccount) {
                resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
                resultSet.setData(vAccountEntity);
                resultSet.setMessage("账户已经存在，请直接登录");
                renderJson(JSON.toJSONString(resultSet));
            } else {
                vAccountEntity.setAccountId(UUID.randomUUID().toString());
                vAccountEntity.setUserId(UUID.randomUUID().toString());
                AccountEntity result = iAccountServices.create(vAccountEntity);
                if (result == null) {
                    vAccountEntity.setAccountId(null);
                    vAccountEntity.setUserId(null);
                    resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
                    resultSet.setData(vAccountEntity);
                    resultSet.setMessage("创建账户失败");
                    renderJson(JSON.toJSONString(resultSet));
                } else {
                    resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
                    resultSet.setData(result);
                    resultSet.setMessage("创建账户成功");
                    renderJson(JSON.toJSONString(resultSet));
                }
            }
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vAccountEntity);
            resultSet.setMessage("参数错误");
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Clear(SessionInterceptor.class)
    @Override
    public void login() {
        String params = this.getPara("p");
        VAccountEntity vAccountEntity = JSON.parseObject(params, VAccountEntity.class);
        IResultSet resultSet = new ResultSet();
        if (vAccountEntity.getIdentity() != null && vAccountEntity.getPassword() != null) {
            AccountEntity result = iAccountServices.retrieve(vAccountEntity);
            if (result == null) {
                resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
                resultSet.setData(vAccountEntity);
                resultSet.setMessage("用户名或密码错误");
                renderJson(JSON.toJSONString(resultSet));
            } else {
                LinkedList<VAccountEntity> vAccountEntities = new LinkedList<>();
                LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(result.getUserId());
                for (AccountEntity accountEntity:accountEntities){
                    vAccountEntities.add(new VAccountEntity(accountEntity.getAccountId(), accountEntity.getIdentity(), accountEntity.getSource(), accountEntity.getUserId()));
                }
                HttpSession session = this.getSession(true);
                String sessionId = session.getId();
                VUserEntity vUserEntity = new VUserEntity(sessionId,result.getUserId(),vAccountEntities);
                session.setAttribute(SessionContext.KEY_USER, vUserEntity);
                resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
                resultSet.setData(vUserEntity);
                resultSet.setMessage("登录成功");
                renderJson(JSON.toJSONString(resultSet));
            }
        } else {
            resultSet.setCode(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(vAccountEntity);
            resultSet.setMessage("参数错误");
            renderJson(JSON.toJSONString(resultSet));
        }
    }

    @Override
    public void logout() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        SessionContext.delSession(vUserEntity.getCs());
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(IResultSet.ResultCode.EXE_SUCCESS.getCode());
        resultSet.setMessage("成功退出");
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {

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

    @Clear(SessionInterceptor.class)
    @Override
    public void password() {

    }

    @Override
    public void portrait() {

    }

    @Override
    public void retrieve() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        HttpSession session = SessionContext.getSession(vUserEntity.getCs());
        if (session == null) {
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode(), vUserEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            VUserEntity sessionUserEntity = (VUserEntity)session.getAttribute(SessionContext.KEY_USER);
            LinkedList<VAccountEntity> result = new LinkedList<>();
            LinkedList<AccountEntity> accountEntities = iAccountServices.retrieveByUserId(sessionUserEntity.getUserId());
            for (AccountEntity accountEntity:accountEntities){
                accountEntity.setAccountId(null);
                result.add(new VAccountEntity(accountEntity));
            }
            vUserEntity.setChildren(result);
            IResultSet iResultSet = new ResultSet(IResultSet.ResultCode.EXE_SUCCESS.getCode(), vUserEntity, "success");
            renderJson(JSON.toJSONString(iResultSet));
        }
    }

    @Clear(SessionInterceptor.class)
    @Before(ManagerInterceptor.class)
    @Override
    public void mLogin() {

    }

    @Clear(SessionInterceptor.class)
    @Before(ManagerInterceptor.class)
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

    @Clear(SessionInterceptor.class)
    @Before(ManagerInterceptor.class)
    @Override
    public void mQueryUsers() {

    }

    @Clear(SessionInterceptor.class)
    @Before(ManagerInterceptor.class)
    @Override
    public void mMark() {
        String params = this.getPara("p");
        VUserEntity vUserEntity = JSON.parseObject(params, VUserEntity.class);
        UserEntity userEntity = null;
        if (vUserEntity.getStatus() == 1) {
            userEntity = iAccountServices.mBlock(vUserEntity);
        } else if (vUserEntity.getStatus() == 2) {
            userEntity = iAccountServices.mUnBlock(vUserEntity);
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

    @Clear(SessionInterceptor.class)
    @Before(ManagerInterceptor.class)
    @Override
    public void mRetrieveAccounts() {

    }
}
