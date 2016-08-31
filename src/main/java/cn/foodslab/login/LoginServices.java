package cn.foodslab.login;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * Created by Pengwei Ding on 2016-07-30 11:17.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录数据服务层
 */
public class LoginServices implements ILoginServices {
    @Override
    public IResultSet retrieveManager(String username, String password) {
        List<Record> records = Db.find("SELECT * FROM manager WHERE username = '" + username + "' AND password = '" + password + "'");
        IResultSet iResultSet = new ResultSet(JSON.toJSONString(records.get(0)));
        return iResultSet;
    }
}
