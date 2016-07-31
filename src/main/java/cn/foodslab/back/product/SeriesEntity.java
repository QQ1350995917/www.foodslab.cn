package cn.foodslab.back.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class SeriesEntity {
    private String seriesId;
    private String label;
    private String description;
    private int queue;
    private int status;
    private String createTime;
    private String updateTime;
    private LinkedList<TypeEntity> typeEntities;

    public SeriesEntity() {
        super();
    }

    public SeriesEntity(String seriesId, String label, int queue, int status) {
        this.seriesId = seriesId;
        this.label = label;
        this.queue = queue;
        this.status = status;
    }

    public SeriesEntity(String seriesId, String label, String description, int queue, int status) {
        this.seriesId = seriesId;
        this.label = label;
        this.description = description;
        this.queue = queue;
        this.status = status;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
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

    public LinkedList<TypeEntity> getTypeEntities() {
        return typeEntities;
    }

    public void setTypeEntities(LinkedList<TypeEntity> typeEntities) {
        this.typeEntities = typeEntities;
    }
}
