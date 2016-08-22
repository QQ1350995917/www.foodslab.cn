package cn.foodslab.back.product;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品管数据服务层
 */
public interface IProductServices {

    /**
     * 获取产品所有系列和参数指定的系列ID下的产品树
     * @param flag
     * @param seriesId 系列ID,可为空
     * @return
     */
    IResultSet series(String flag,String seriesId);

    /**
     * 获取指定的型号的产品树
     * @param typeId
     * @return
     */
    IResultSet type(String typeId);

    /**
     * 获取所有的推荐产品
     * @return
     */
    IResultSet recommend();



    /**
     * 获取所有的产品树数据
     * @return
     */
    IResultSet retrieve();

    IResultSet createSeries(SeriesEntity seriesEntity);

    IResultSet createType(TypeEntity typeEntity);

    IResultSet createFormat(FormatEntity formatEntity);

    IResultSet updateWeight(FormatEntity formatEntity);

    IResultSet swapWeight(FormatEntity formatEntity1,FormatEntity formatEntity2);

    IResultSet updateSeries(SeriesEntity seriesEntity);

    IResultSet updateType(TypeEntity typeEntity);

    IResultSet updateTypeDescription(TypeEntity typeEntity);

    IResultSet updateTypeImage(TypeEntity typeEntity);

    IResultSet updateFormat(FormatEntity formatEntity);



    /**
     * 反转读取，从规格开始逆向读取数据，该接口适用于推荐功能
     * @return
     */
    IResultSet convert();

    IResultSet retrieveSeries(String seriesId);

    IResultSet retrieveType(String typeId);

    IResultSet retrieveFormat(String formatId);



}
