package cn.foodslab.back.menu;

import cn.foodslab.dba.DBConnector;
import com.alibaba.fastjson.JSON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 11:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 对管理界面菜单的操作
 */
public class MenuDao implements IMenuDao {

    @Override
    public boolean initMenus(LinkedList<MenuEntity> menuEntities) throws Exception {
        boolean result = false;
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.createStatement();
            for (MenuEntity menuEntity : menuEntities) {
                preparedStatement.addBatch(menuEntity.toInsertSQLString());
            }
            preparedStatement.executeBatch();
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }

        return result;
    }

    @Override
    public LinkedList<MenuEntity> getMenus() throws Exception {
        LinkedList<MenuEntity> result = new LinkedList<>();
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String sql = "SELECT * FROM menu ORDER BY queue;";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                MenuEntity menuEntity = new MenuEntity(
                        resultSet.getString("menuId"),
                        resultSet.getString("label"),
                        resultSet.getInt("level"),
                        resultSet.getInt("queue"),
                        resultSet.getString("method"),
                        resultSet.getString("positionId"),
                        resultSet.getString("pid"),
                        resultSet.getInt("status"),
                        resultSet.getString("createTime"),
                        resultSet.getString("updateTime"));
                result.add(menuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public LinkedList<MenuEntity> getMenusByLevel(int level) throws Exception {
        LinkedList<MenuEntity> result = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String sql = "SELECT * FROM menu WHERE level = ? ORDER BY queue;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,level);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MenuEntity menuEntity = new MenuEntity(
                        resultSet.getString("menuId"),
                        resultSet.getString("label"),
                        resultSet.getInt("level"),
                        resultSet.getInt("queue"),
                        resultSet.getString("method"),
                        resultSet.getString("positionId"),
                        resultSet.getString("pid"),
                        resultSet.getInt("status"),
                        resultSet.getString("createTime"),
                        resultSet.getString("updateTime"));
                result.add(menuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public LinkedList<MenuEntity> getMenusByPosition(String position) throws Exception {
        LinkedList<MenuEntity> result = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String sql = "SELECT * FROM menu WHERE positionId = ? ORDER BY queue;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, position);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MenuEntity menuEntity = new MenuEntity(
                        resultSet.getString("menuId"),
                        resultSet.getString("label"),
                        resultSet.getInt("level"),
                        resultSet.getInt("queue"),
                        resultSet.getString("method"),
                        resultSet.getString("positionId"),
                        resultSet.getString("pid"),
                        resultSet.getInt("status"),
                        resultSet.getString("createTime"),
                        resultSet.getString("updateTime"));
                result.add(menuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public LinkedList<MenuEntity> getSubMenus(String pid) throws Exception {
        LinkedList<MenuEntity> result = new LinkedList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String sql = "SELECT * FROM menu WHERE pid = ? AND menuId != pid ORDER BY queue;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MenuEntity menuEntity = new MenuEntity(
                        resultSet.getString("menuId"),
                        resultSet.getString("label"),
                        resultSet.getInt("level"),
                        resultSet.getInt("queue"),
                        resultSet.getString("method"),
                        resultSet.getString("positionId"),
                        resultSet.getString("pid"),
                        resultSet.getInt("status"),
                        resultSet.getString("createTime"),
                        resultSet.getString("updateTime"));
                result.add(menuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public String apiGetMenus() throws Exception {
        LinkedList<MenuEntity> menuList = getMenus();
        String jsonString = JSON.toJSONString(menuList);
        return jsonString;
    }

    @Override
    public String apiGetMenusByLevel(int level) throws Exception {
        LinkedList<MenuEntity> menuList = getMenusByLevel(level);
        String jsonString = JSON.toJSONString(menuList);
        return jsonString;
    }

    @Override
    public String apiGetMenusByPosition(String position) throws Exception {
        LinkedList<MenuEntity> menuList = getMenusByPosition(position);
        String jsonString = JSON.toJSONString(menuList);
        return jsonString;
    }

    @Override
    public String apiGetSubMenus(String pid) throws Exception {
        LinkedList<MenuEntity> menuList = getSubMenus(pid);
        String jsonString = JSON.toJSONString(menuList);
        return jsonString;
    }

}
