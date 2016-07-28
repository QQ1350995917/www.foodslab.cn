package cn.foodslab.dba;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-07-28 10:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class DBConnector {
    private static String url;
    private static String username;
    private static String password;

    static{
        try{
            Properties prop = new Properties();
            prop.load(DBConnector.class.getClassLoader().getResourceAsStream("db.properties"));
            String driver = prop.getProperty("driver");
            Class.forName(driver);
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username,password);
    }


    public static void free(Connection conn,Statement ps,ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
            }catch (Exception e) {}
            rs = null;
        }
        if(ps!=null){
            try{
                ps.close();
            }catch (Exception e) {}
            ps = null;
        }
        if(conn!=null){
            try{
                conn.close();
            }catch (Exception e) {}
            conn = null;
        }
    }
}

