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

    IResultSet updateWeight(FormatEntity formatEntity);

    IResultSet updateSeries(SeriesEntity seriesEntity);

    IResultSet updateType(TypeEntity typeEntity);

    IResultSet updateTypeDescription(TypeEntity typeEntity);

    IResultSet updateTypeImage(TypeEntity typeEntity);

    IResultSet updateFormat(FormatEntity formatEntity);

    IResultSet retrieve();

    /**
     * 反转读取，从规格开始逆向读取数据，该接口适用于推荐功能
     * @return
     */
    IResultSet convert();

    IResultSet retrieveSeries(String seriesId);

    IResultSet retrieveType(String typeId);

    IResultSet retrieveFormat(String formatId);



}
