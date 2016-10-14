package cn.foodslab.controller.product;

import cn.foodslab.service.product.TypeEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 18:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VTypeEntity extends TypeEntity {
    private String sessionId;
    private LinkedList<VFormatEntity> children;
    private VSeriesEntity parent;

    public VTypeEntity() {
        super();
    }

    public VTypeEntity(TypeEntity typeEntity) {
        this.setSeriesId(typeEntity.getSeriesId());
        this.setTypeId(typeEntity.getTypeId());
        this.setLabel(typeEntity.getLabel());
        this.setSummary(typeEntity.getSummary());
        this.setDirections(typeEntity.getDirections());
        this.setStatus(typeEntity.getStatus());
    }

    public VTypeEntity(String sessionId, String seriesId, String typeId, String label, String summary, String directions, int queue, int status) {
        this.sessionId = sessionId;
        this.setSeriesId(seriesId);
        this.setTypeId(typeId);
        this.setLabel(label);
        this.setSummary(summary);
        this.setDirections(directions);
        this.setQueue(queue);
        this.setStatus(status);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LinkedList<VFormatEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VFormatEntity> children) {
        this.children = children;
    }

    public VSeriesEntity getParent() {
        return parent;
    }

    public void setParent(VSeriesEntity parent) {
        this.parent = parent;
    }
}
