package cn.foodslab.back.manager;

import cn.foodslab.dba.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Pengwei Ding on 2016-07-28 20:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ManagerDao implements IManagerDao {

    @Override
    public boolean initManager(ManagerEntity managerEntity) throws Exception {
        boolean result = false;
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.createStatement();
            result = preparedStatement.execute(managerEntity.toInsterSQLString());
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public ManagerEntity createManager(String username, String password, String pid, String... menuIds) throws Exception {
        return null;
    }

    @Override
    public ManagerEntity updateManagerUsername(ManagerEntity managerEntity) throws Exception {
        return null;
    }

    @Override
    public ManagerEntity updateManagerStatus(ManagerEntity managerEntity) throws Exception {
        return null;
    }

    @Override
    public ManagerEntity updateManagerPassword(ManagerEntity managerEntity) throws Exception {
        return null;
    }

    @Override
    public ManagerEntity updateManagerMenuIds(ManagerEntity managerEntity) throws Exception {
        return null;
    }

    @Override
    public ManagerEntity updateManager(ManagerEntity managerEntity) throws Exception {
        return null;
    }
}
