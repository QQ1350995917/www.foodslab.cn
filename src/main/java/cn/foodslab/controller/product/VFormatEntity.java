package cn.foodslab.controller.product;

import cn.foodslab.service.product.FormatEntity;

/**
 * Created by Pengwei Ding on 2016-09-23 11:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VFormatEntity extends FormatEntity {
    private String cs;
    private VTypeEntity parent;

    public VFormatEntity() {
        super();
    }

    public VFormatEntity(FormatEntity formatEntity) {
        this.setTypeId(formatEntity.getTypeId());
        this.setFormatId(formatEntity.getFormatId());
        this.setLabel(formatEntity.getLabel());
        this.setMeta(formatEntity.getMeta());
        this.setAmount(formatEntity.getAmount());
        this.setAmountMeta(formatEntity.getAmountMeta());
        this.setPrice(formatEntity.getPrice());
        this.setPriceMeta(formatEntity.getPriceMeta());
        this.setPostage(formatEntity.getPostage());
        this.setPostageMeta(formatEntity.getPostageMeta());
        this.setPricing(formatEntity.getPricing());
        this.setPricingDiscount(formatEntity.getPricingDiscount());
        this.setPricingStart(formatEntity.getPricingStart());
        this.setPricingEnd(formatEntity.getPricingEnd());
        this.setPricingStatus(formatEntity.getPricingStatus());
        this.setExpressCount(formatEntity.getExpressCount());
        this.setExpressName(formatEntity.getExpressName());
        this.setExpressStart(formatEntity.getExpressStart());
        this.setExpressEnd(formatEntity.getExpressEnd());
        this.setExpressStatus(formatEntity.getExpressStatus());
        this.setGiftCount(formatEntity.getGiftCount());
        this.setGiftLabel(formatEntity.getGiftLabel());
        this.setGiftId(formatEntity.getGiftId());
        this.setGiftStart(formatEntity.getGiftStart());
        this.setGiftEnd(formatEntity.getGiftEnd());
        this.setGiftStatus(formatEntity.getGiftStatus());
        this.setWeight(formatEntity.getWeight());
        this.setQueue(formatEntity.getQueue());
        this.setStatus(formatEntity.getStatus());
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public VTypeEntity getParent() {
        return parent;
    }

    public void setParent(VTypeEntity parent) {
        this.parent = parent;
    }


}
