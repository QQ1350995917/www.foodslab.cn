package cn.foodslab.back.product;

import cn.foodslab.back.image.ImageEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 21:39.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TypeEntity {
    private String typeId;
    private String label;
    private String description;
    private String detail;
    private String crafts;
    private int queue;
    private int status;
    private String seriesId;
    private String createTime;
    private String updateTime;
    private LinkedList<ImageEntity> imageEntities;
    private LinkedList<FormatEntity> formatEntities;

    public TypeEntity() {
    }

    public TypeEntity(String typeId, String label, String description, String detail, String crafts, int queue, int status, String seriesId) {
        this.typeId = typeId;
        this.label = label;
        this.description = description;
        this.detail = detail;
        this.crafts = crafts;
        this.queue = queue;
        this.status = status;
        this.seriesId = seriesId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCrafts() {
        return crafts;
    }

    public void setCrafts(String crafts) {
        this.crafts = crafts;
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

    public LinkedList<FormatEntity> getFormatEntities() {
        return formatEntities;
    }

    public void setFormatEntities(LinkedList<FormatEntity> formatEntities) {
        this.formatEntities = formatEntities;
    }
}
