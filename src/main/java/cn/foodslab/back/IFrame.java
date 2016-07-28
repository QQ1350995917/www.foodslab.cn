package cn.foodslab.back;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 11:19.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 对管理界面菜单的操作
 */
public interface IFrame {

    /**
     * 查询所有的菜单对象
     * @return
     */
    LinkedList<FrameMenuEntity> getMenuList();

    /**
     * 查询所有的菜单对象
     * @return
     */
    String getMenuListJson();


}
