package cn.foodslab.controller.product;

import cn.foodslab.service.product.SeriesEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 17:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VSeriesEntity {
    private String sessionId;
    private String seriesId;
    private String label;
    private int status = -2;
    private LinkedList<VTypeEntity> children;

    public VSeriesEntity() {
        super();
    }

    public VSeriesEntity(SeriesEntity seriesEntity) {
        this.seriesId = seriesEntity.getSeriesId();
        this.label = seriesEntity.getLabel();
    }

    public VSeriesEntity(String sessionId, String seriesId, String label) {
        this.sessionId = sessionId;
        this.seriesId = seriesId;
        this.label = label;
    }

    public VSeriesEntity(String sessionId, String seriesId, String label, int status) {
        this.sessionId = sessionId;
        this.seriesId = seriesId;
        this.label = label;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LinkedList<VTypeEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VTypeEntity> children) {
        this.children = children;
    }
}
