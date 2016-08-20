package cn.foodslab.common.meta;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-19 11:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IMetaServices {

    IResultSet retrieveUnit();

    IResultSet retrieveAddress(String pid);

    IResultSet retrieveLink();


}
