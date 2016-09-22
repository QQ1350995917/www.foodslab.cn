package cn.foodslab.service.product;

import cn.foodslab.service.image.ImageEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 17:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeServices implements ITypeServices {

    @Override
    public TypeEntity create(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public TypeEntity update(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public TypeEntity updateStatus(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public LinkedList<TypeEntity> retrieveInSeries(String seriesId) {
        return null;
    }

    @Override
    public TypeEntity retrieveById(String typeId) {
        return null;
    }

    @Override
    public TypeEntity retrieveInSeriesByLabel(String seriesId, String typeLabel) {
        return null;
    }

    @Override
    public TypeEntity updateSummary(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public TypeEntity updateDirections(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public TypeEntity updateImage(String typeId, ImageEntity imageEntity) {
        return null;
    }

    @Override
    public TypeEntity deleteImage(String typeId, ImageEntity imageEntity) {
        return null;
    }
}
