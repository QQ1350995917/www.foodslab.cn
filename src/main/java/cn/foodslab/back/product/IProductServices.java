package cn.foodslab.back.product;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IProductServices {

    IResultSet createSeries(SeriesEntity seriesEntity);

    IResultSet createType(TypeEntity typeEntity);

    IResultSet createFormat(FormatEntity formatEntity);

    IResultSet updateSeries(SeriesEntity seriesEntity);

    IResultSet updateType(TypeEntity typeEntity);

    IResultSet updateFormat(FormatEntity formatEntity);

    IResultSet retrieve();

    IResultSet retrieveSeries(String seriesId);

    IResultSet retrieveType(String typeId);

    IResultSet retrieveFormat(String formatId);

}
