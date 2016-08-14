package cn.foodslab.back.link;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ILinkServices {

    IResultSet retrieve();

    IResultSet create(LinkEntity linkEntity);

    IResultSet update(LinkEntity linkEntity);
}
