package cn.foodslab.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISeriesServices {
    /**
     * 后台接口
     * 创建一个系列
     * @param seriesEntity 待创建的数据对象
     * @return success 数据对象 fail null
     */
    SeriesEntity mCreate(SeriesEntity seriesEntity);

    /**
     * 后台接口
     * 创建一个系列
     * @param seriesEntity 待更新的数据对象
     * @return success 数据对象 fail null
     */
    SeriesEntity mUpdate(SeriesEntity seriesEntity);

    /**
     * 后台接口
     * 创建一个系列
     * @param seriesEntity 待更新状态的数据对象，除了ID和status，可不包含其他字段
     * @return success 数据对象 fail null
     */
    SeriesEntity mUpdateStatus(SeriesEntity seriesEntity);

    /**
     * 前台接口
     * 读取所有的系列
     * @return success 数据集合 fail null
     */
    LinkedList<SeriesEntity> retrieves();

    /**
     * 后台接口
     * 读取所有的系列
     * @return success 数据集合 fail null
     */
    LinkedList<SeriesEntity> mRetrieves();

    /**
     * 通过ID精确查找
     * @param seriesId 系列ID
     * @return success 数据对象 fail null
     */
    SeriesEntity retrieveById(String seriesId);

    /**
     * 通过label查找 用户重名查找
     * @param seriesEntity 系列数据对象
     * @return success 数据对象 fail null
     */
    SeriesEntity retrieveByLabel(SeriesEntity seriesEntity);
}
