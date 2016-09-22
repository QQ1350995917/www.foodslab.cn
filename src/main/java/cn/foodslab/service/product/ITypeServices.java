package cn.foodslab.service.product;

import cn.foodslab.service.image.ImageEntity;

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
    TypeEntity create(TypeEntity typeEntity);

    /**
     * 更新类型
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity update(TypeEntity typeEntity);

    /**
     * 更新类型的状态
     * @param typeEntity 类型数据
     * @return success 类型数据 fail null
     */
    TypeEntity updateStatus(TypeEntity typeEntity);

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
    TypeEntity retrieveById(String typeId);

    /**
     * 在系列下读取指定类型名称的类型
     * @param seriesId 系列ID
     * @param typeLabel 类型名称
     * @return success 类型数据 fail null
     */
    TypeEntity retrieveInSeriesByLabel(String seriesId,String typeLabel);

    /**
     * 更新类型的简介
     * @param typeEntity
     * @return
     */
    TypeEntity updateSummary(TypeEntity typeEntity);

    /**
     * 更新类型的说明
     * @param typeEntity
     * @return
     */
    TypeEntity updateDirections(TypeEntity typeEntity);

    /**
     * 更新类型下指定的的展示图片
     * @param typeId
     * @param imageEntity
     * @return
     */
    TypeEntity updateImage(String typeId,ImageEntity imageEntity);

    /**
     * 删除类型下的指定的展示图片
     * @param typeId
     * @param imageEntity
     * @return
     */
    TypeEntity deleteImage(String typeId,ImageEntity imageEntity);

}
