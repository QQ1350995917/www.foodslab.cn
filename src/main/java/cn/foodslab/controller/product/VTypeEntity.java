package cn.foodslab.controller.product;

import cn.foodslab.service.product.TypeEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 18:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VTypeEntity extends TypeEntity {
    private String cs;
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
        this.setQueue(typeEntity.getQueue());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
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

    public boolean checkCreateParams() {
        if (this.getLabel() == null || this.getLabel().trim().equals("")
                || this.getSeriesId() == null || this.getSeriesId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUpdateParams() {
        if (this.getSeriesId() == null || this.getSeriesId().trim().equals("")
                || this.getTypeId() == null || this.getTypeId().trim().equals("")
                || this.getLabel() == null || this.getLabel().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkMarkParams() {
        if (this.getTypeId() == null || this.getTypeId().equals("")
                || (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkTypeId() {
        if (this.getTypeId() == null || this.getTypeId().equals("")) {
            return false;
        } else {
            return true;
        }
    }


}
