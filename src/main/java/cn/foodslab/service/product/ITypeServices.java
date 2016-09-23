package cn.foodslab.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ITypeServices {

    /**
     * 在系列下创建一个新的类型
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mCreate(TypeEntity typeEntity);

    /**
     * 更新类型
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdate(TypeEntity typeEntity);

    /**
     * 更新类型的状态
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdateStatus(TypeEntity typeEntity);

    /**
     * 读取系列下的类型
     * @param seriesId 系列ID
     * @return success 类型数据集 fail null
     */
    LinkedList<TypeEntity> mRetrieveInSeries(String seriesId);

    /**
     * 读取系列下的类型
     * @param seriesId 系列ID
     * @return success 类型数据集 fail null
     */
    LinkedList<TypeEntity> retrieveInSeries(String seriesId);

    /**
     * 通过类型的ID精确读取类型信息
     * @param typeId 类型ID
     * @return success 类型数据 fail null
     */
    TypeEntity mRetrieveById(String typeId);

    /**
     * 通过类型的ID精确读取类型信息
     * @param typeId 类型ID
     * @return success 类型数据 fail null
     */
    TypeEntity retrieveById(String typeId);

    /**
     * 在系列下读取指定类型名称的类型
     * @param typeEntity 系列ID
     * @return success 类型数据 fail null
     */
    TypeEntity mRetrieveInSeriesByLabel(TypeEntity typeEntity);

    /**
     * 更新类型的简介
     * @param typeEntity
     * @return
     */
    TypeEntity mUpdateSummary(TypeEntity typeEntity);

    /**
     * 更新类型的说明
     * @param typeEntity
     * @return
     */
    TypeEntity mUpdateDirections(TypeEntity typeEntity);

}
