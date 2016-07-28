package cn.foodslab.back;

import cn.foodslab.dba.DBConnector;
import com.alibaba.fastjson.JSON;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 11:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 对管理界面菜单的操作
 */
public class FrameDao implements IFrame {

    @Override
    public LinkedList<FrameMenuEntity> getMenuList() {
        LinkedList<FrameMenuEntity> frameMenuEntities = new LinkedList<>();
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            String sql = "SELECT * FROM menu ORDER BY menuOrder;";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while(resultSet.next()){
                FrameMenuEntity frameMenuEntity = new FrameMenuEntity(resultSet.getString("menuId"), resultSet.getString("menuLabel"), resultSet.getString("levelId"), resultSet.getString("levelLabel"), resultSet.getInt("menuOrder"), resultSet.getString("menuCall"), resultSet.getString("createTime"), resultSet.getString("updateTime"));
                frameMenuEntities.add(frameMenuEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnector.free(connection, preparedStatement, resultSet);
        }
        return frameMenuEntities;
    }

    @Override
    public String getMenuListJson() {
        LinkedList<FrameMenuEntity> menuList = getMenuList();
        String jsonString = JSON.toJSONString(menuList);
        return jsonString;
    }
}
