package cn.foodslab.back.users;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-16 13:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class UsersServices implements IUsersServices{

    @Override
    public IResultSet retrieve() {
        LinkedList<Map<String,Object>> result = new LinkedList<>();
        List<Record> userRecords = Db.find("SELECT userId , status FROM user WHERE status != -1");
        for (Record userRecord:userRecords){
            Map<String, Object> userEntity = userRecord.getColumns();
            List<Record> accountRecords = Db.find("SELECT * FROM user_account WHERE userId = ? ", userEntity.get("userId"));
            LinkedList<Map<String,Object>> accountList = new LinkedList<>();
            for (Record accountRecord:accountRecords){
                Map<String, Object> accountEntity = accountRecord.getColumns();
                accountList.add(accountEntity);
            }
            userEntity.put("children", accountList);
            result.add(userEntity);
        }
        IResultSet resultSet = new ResultSet();
        resultSet.setCode(200);
        resultSet.setData(result);
        resultSet.setMessage("ok");
        return resultSet;
    }
}
