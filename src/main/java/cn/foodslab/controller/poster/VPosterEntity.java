package cn.foodslab.controller.poster;

import cn.foodslab.service.poster.PosterEntity;

/**
 * Created by Pengwei Ding on 2016-10-11 16:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VPosterEntity extends PosterEntity {
    private String posterId1;
    private int weight1;
    private String posterId2;
    private int weight2;

    public VPosterEntity() {
        super();
    }

    public VPosterEntity(PosterEntity posterEntity) {
        this.setPosterId(posterEntity.getPosterId());
        this.setStatus(posterEntity.getStatus());
        this.setClickable(posterEntity.getClickable());
        this.setHref(posterEntity.getHref());
        this.setFileId(posterEntity.getFileId());
        this.setWeight(posterEntity.getWeight());
        this.setStart(posterEntity.getStart());
        this.setEnd(posterEntity.getEnd());
        this.setCreateTime(posterEntity.getCreateTime());
        this.setUpdateTime(posterEntity.getUpdateTime());
    }

    public String getPosterId1() {
        return posterId1;
    }

    public void setPosterId1(String posterId1) {
        this.posterId1 = posterId1;
    }

    public int getWeight1() {
        return weight1;
    }

    public void setWeight1(int weight1) {
        this.weight1 = weight1;
    }

    public String getPosterId2() {
        return posterId2;
    }

    public void setPosterId2(String posterId2) {
        this.posterId2 = posterId2;
    }

    public int getWeight2() {
        return weight2;
    }

    public void setWeight2(int weight2) {
        this.weight2 = weight2;
    }
}
