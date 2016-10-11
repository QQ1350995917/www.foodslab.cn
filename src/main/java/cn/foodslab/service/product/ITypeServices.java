package cn.foodslab.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ITypeServices {

    /**
     * 用户接口
     * 读取系列下的类型集合
     *
     * @param seriesEntity 最少包含系列ID的数据对象
     * @return success 类型数据集 fail null
     */
    LinkedList<TypeEntity> retrievesInSeries(SeriesEntity seriesEntity);

    /**
     * 用户接口
     * 通过类型的ID精确读取类型信息
     *
     * @param typeId 类型ID
     * @return success 类型数据 fail null
     */
    TypeEntity retrieveById(String typeId);

    /**
     * 管理员接口
     * 检测在系列下是否已经存在类型名称
     *
     * @param typeName 类型名称
     * @param seriesId 系列ID
     * @return true exist false not exist
     */
    boolean mExistInSeries(String typeName, String seriesId);

    /**
     * 管理员接口
     * 在系列下创建一个新的类型
     *
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mCreate(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 更新类型
     *
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdate(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 禁用类型
     *
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mBlock(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 启用类型
     *
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mUnBlock(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 删除类型
     *
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity mDelete(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 读取系列下的类型
     *
     * @param seriesEntity 最少包含系列ID的数据对象
     * @return success 类型数据集 fail null
     */
    LinkedList<TypeEntity> mRetrievesInSeries(SeriesEntity seriesEntity);

    /**
     * 管理员接口
     * 通过类型的ID精确读取类型信息
     *
     * @param typeId 类型ID
     * @return success 类型数据 fail null
     */
    TypeEntity mRetrieveById(String typeId);

    /**
     * 管理员接口
     * 更新类型的简介
     *
     * @param typeEntity 类型数据对象
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdateSummary(TypeEntity typeEntity);

    /**
     * 管理员接口
     * 更新类型的说明
     *
     * @param typeEntity 类型数据对象
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdateDirections(TypeEntity typeEntity);

    /**
     * 管理员接口
     * @param typeEntity 类型数据对象
     * @return success 类型数据 fail null
     */
    TypeEntity mUpdateImage(TypeEntity typeEntity);

    TypeEntity mImageDelete(TypeEntity typeEntity);


}
