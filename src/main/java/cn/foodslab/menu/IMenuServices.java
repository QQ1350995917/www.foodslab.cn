package cn.foodslab.menu;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 14:33.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMenuServices {
    /**
     * @param level
     * @return
     */
    IResultSet retrieveMenusByLevel(int level);

    /**
     * 通过状态查询菜单
     * @param status
     * @return
     */
    IResultSet retrieveMenusByStatus(int status);
    IResultSet retrieveMenusByIds(String... ids);
}
