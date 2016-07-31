package cn.foodslab.back.manager;

import cn.foodslab.back.init.InitManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pengwei Ding on 2016-07-28 20:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ManagerTest {

    public static final String pid = InitManager.id;

    @Before
    public void testInsertSubManagerBefore() throws Exception {
        URL url = new URL("http://localhost:8080/manager");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int index = -1;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void testExistManagerName() throws Exception {

    }

    @Test
    public void testInsertSubManager() throws Exception {
    }

    @Test
    public void testUpdateSubManagerUserName() throws Exception {
    }

    @Test
    public void testUpdateSubManagerStatus() throws Exception {
    }

    @Test
    public void testUpdateSubManagerPassword() throws Exception {
    }

    @Test
    public void testUpdateSubManagerMenuMapping() throws Exception {
    }


    @Test
    public void testSelectManMenusByManagerId() throws Exception {
    }

    @After
    public void testInsertSubManagerAfter() throws Exception {

    }



}
