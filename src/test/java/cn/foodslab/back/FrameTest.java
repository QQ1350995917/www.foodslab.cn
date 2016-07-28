package cn.foodslab.back;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 11:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FrameTest {
    FrameDao frameDao = new FrameDao();

    @Before
    public void testFrameMenuListBefore() throws Exception{

    }

    @Test
    public void testGetFrameMenuList() throws Exception {
        LinkedList<FrameMenuEntity> menuList = frameDao.getMenuList();
        Assert.assertEquals(13,menuList.size());
    }

    @Test
    public void testGetFrameMenuListJson() throws Exception {
        String menuListJson = frameDao.getMenuListJson();
        System.out.println(menuListJson);
        Assert.assertNotNull(menuListJson);
    }

    @After
    public void testFrameMenuListAfter() throws Exception{

    }

}
