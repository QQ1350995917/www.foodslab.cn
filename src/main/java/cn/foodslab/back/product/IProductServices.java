package cn.foodslab.back.product;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IProductServices {

    IResultSet isExistSeriesName(String seriesName);

    IResultSet isExistTypeNameInSeries(SeriesEntity seriesEntity,String typeName);

    IResultSet isExistFormatNameInType(TypeEntity typeEntity,String formatName);

    IResultSet createSeries(SeriesEntity seriesEntity);

    IResultSet createType(SeriesEntity seriesEntity,TypeEntity typeEntity);

    IResultSet createFormat(TypeEntity typeEntity,FormatEntity formatEntity);

    IResultSet updateSeries(SeriesEntity seriesEntity);

    IResultSet updateType(SeriesEntity seriesEntity,TypeEntity typeEntity);

    IResultSet updateFormat(TypeEntity typeEntity,FormatEntity formatEntity);

    IResultSet retrieveSeries();

    IResultSet retrieveType();

    IResultSet retrieveTypeInSeries(SeriesEntity seriesEntity);

    IResultSet retrieveFormat();

    IResultSet retrieveFormatInType(TypeEntity typeEntity);


}
