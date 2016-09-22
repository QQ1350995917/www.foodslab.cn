package cn.foodslab.service.product;

import cn.foodslab.service.image.ImageEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 21:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeEntity {
    private String seriesId;
    private String typeId;
    private String label;
    private String summary;
    private String directions;
    private int queue;
    private int status;
    private String createTime;
    private String updateTime;
    private LinkedList<ImageEntity> imageEntities;
    private LinkedList<FormatEntity> children;
    private SeriesEntity parent;

    public TypeEntity() {
    }

    public TypeEntity(String seriesId,String label) {
        this.label = label;
        this.seriesId = seriesId;
    }

    public TypeEntity(String seriesId,String typeId,String label) {
        this.seriesId = seriesId;
        this.typeId = typeId;
        this.label = label;
    }

    public TypeEntity(String seriesId, String typeId, String label, String summary, String directions, int queue, int status) {
        this.seriesId = seriesId;
        this.typeId = typeId;
        this.label = label;
        this.summary = summary;
        this.directions = directions;
        this.queue = queue;
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public LinkedList<ImageEntity> getImageEntities() {
        return imageEntities;
    }

    public void setImageEntities(LinkedList<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
    }

    public LinkedList<FormatEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<FormatEntity> children) {
        this.children = children;
    }

    public SeriesEntity getParent() {
        return parent;
    }

    public void setParent(SeriesEntity parent) {
        this.parent = parent;
    }
}
