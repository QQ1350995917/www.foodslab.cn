package cn.foodslab.back.menu;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 16:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 后台管理的菜单数据DAO操作
 */
public interface IMenuDao {

    /**
     * 初始化菜单对象,该接口在为首初始化时候调用
     * @param menuEntities
     * @return 返回操作的结果
     * @throws Exception
     */
    boolean initMenus(LinkedList<MenuEntity> menuEntities) throws Exception;

    /**
     * 查询所有的菜单对象
     * @return
     */
    LinkedList<MenuEntity> getMenus() throws Exception;

    /**
     * 查询指定级别下的所有的菜单对象
     * @param level
     * @return 返回操作成功的数据
     * @throws Exception
     */
    LinkedList<MenuEntity> getMenusByLevel(int level) throws Exception;

    /**
     * 查询指定位置下的所有的菜单对象
     * @param position 位置标记
     * @return 返回操作成功的数据
     * @throws Exception
     */
    LinkedList<MenuEntity> getMenusByPosition(String position) throws Exception;

    /**
     * 查询子菜单
     * @param pid 父菜单的ID
     * @return 返回操作成功的数据
     * @throws Exception
     */
    LinkedList<MenuEntity> getSubMenus(String pid) throws Exception;

    /**
     * API接口查询所有的菜单对象并转化为JSON字符串
     * @return
     */
    String apiGetMenus() throws Exception;

    String apiGetMenusByLevel(int level) throws Exception;

    String apiGetMenusByPosition(String position) throws Exception;

    String apiGetSubMenus(String pid) throws Exception;

}
