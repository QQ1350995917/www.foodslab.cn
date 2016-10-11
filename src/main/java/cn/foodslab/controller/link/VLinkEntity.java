package cn.foodslab.controller.link;

import cn.foodslab.service.link.LinkEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-10-11 15:29.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VLinkEntity extends LinkEntity {

    private String linkId1;
    private int weight1;
    private String linkId2;
    private int weight2;

    private LinkedList<VLinkEntity> children;

    public VLinkEntity() {
        super();
    }

    public String getLinkId1() {
        return linkId1;
    }

    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1;
    }

    public int getWeight1() {
        return weight1;
    }

    public void setWeight1(int weight1) {
        this.weight1 = weight1;
    }

    public String getLinkId2() {
        return linkId2;
    }

    public void setLinkId2(String linkId2) {
        this.linkId2 = linkId2;
    }

    public int getWeight2() {
        return weight2;
    }

    public void setWeight2(int weight2) {
        this.weight2 = weight2;
    }

    public LinkedList<VLinkEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VLinkEntity> children) {
        this.children = children;
    }
}
