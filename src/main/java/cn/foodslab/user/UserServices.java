package cn.foodslab.user;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Pengwei Ding on 2016-08-16 10:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UserServices implements IUserServices {

    @Override
    public IResultSet createAccount(AccountEntity accountEntity) {
        Record userRecord = new Record()
                .set("userId", accountEntity.getUserId())
                .set("status", 1);
        Record userAccountRecord = new Record()
                .set("accountId", accountEntity.getAccountId())
                .set("telephone", accountEntity.getTelephone())
                .set("password", accountEntity.getPassword())
                .set("userId", accountEntity.getUserId());
        boolean user = Db.save("user", userRecord);
        boolean userAccount = Db.save("user_account", userAccountRecord);
        IResultSet resultSet = new ResultSet();
        if (user && userAccount) {
            resultSet.setCode(200);
            resultSet.setData(accountEntity);
            resultSet.setMessage("创建成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("创建失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet updateAccount(AccountEntity accountEntity) {
        String head = "UPDATE user_account SET ";
        String set = "";
        if (accountEntity.getTelephone() != null){
            set = set + " , telephone = '" + accountEntity.getTelephone() + "' ";
        }
        if (accountEntity.getPassword() != null){
            set = set + " , password = '" + accountEntity.getPassword() + "' ";
        }
        if (accountEntity.getName() != null){
            set = set + " , name = '" + accountEntity.getName() + "' ";
        }
        if (accountEntity.getGender() != -1){
            set = set + " , gender = '" + accountEntity.getGender() + "' ";
        }
        if (accountEntity.getAddress() != null){
            set = set + " , address = '" + accountEntity.getAddress() + "' ";
        }
        if (accountEntity.getBirthday() != null){
            set = set + " , birthday = '" + accountEntity.getBirthday() + "' ";
        }

        String where = " WHERE accountId = '" + accountEntity.getAccountId() + "'";

        String sql = head + set.replaceFirst(","," ") + where;

        int update = Db.update(sql);
        IResultSet resultSet = new ResultSet();
        if (update == 1) {
            resultSet.setCode(200);
            resultSet.setData(accountEntity);
            resultSet.setMessage("更新成功");
        } else {
            resultSet.setCode(500);
            resultSet.setMessage("更新失败");
        }
        return resultSet;
    }

    @Override
    public IResultSet bindAccount(UserEntity userEntity, AccountEntity accountEntity) {
        return null;
    }

    @Override
    public IResultSet createReceiver(AccountEntity accountEntity, ReceiverEntity receiverEntity) {
        return null;
    }

    @Override
    public IResultSet updateReceiver(ReceiverEntity receiverEntity) {
        return null;
    }

    @Override
    public IResultSet retrieveAccount(UserEntity userEntity) {
        return null;
    }

    @Override
    public IResultSet retrieveReceiver(AccountEntity accountEntity) {
        return null;
    }
}
