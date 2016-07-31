package cn.foodslab.back.menu;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 14:33.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMenuServices {
    /**
     *
     * @param level
     * @return
     */
    IResultSet retrieveMenusByLevel(int level);
    IResultSet retrieveMenusByIds(String... ids);
}
