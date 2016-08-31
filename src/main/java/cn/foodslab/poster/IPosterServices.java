package cn.foodslab.poster;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IPosterServices {

    IResultSet retrieve();

    IResultSet create(PosterEntity posterEntity);

    IResultSet update(PosterEntity posterEntity);

}
