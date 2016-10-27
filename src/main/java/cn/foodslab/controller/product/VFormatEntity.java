package cn.foodslab.controller.product;

import cn.foodslab.service.product.FormatEntity;

/**
 * Created by Pengwei Ding on 2016-09-23 11:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VFormatEntity extends FormatEntity {
    private String cs;
    private String formatId1;
    private int weight1;
    private String formatId2;
    private int weight2;
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
//        this.setGiftCount(formatEntity.getGiftCount());
//        this.setGiftLabel(formatEntity.getGiftLabel());
//        this.setGiftId(formatEntity.getGiftId());
//        this.setGiftStart(formatEntity.getGiftStart());
//        this.setGiftEnd(formatEntity.getGiftEnd());
//        this.setGiftStatus(formatEntity.getGiftStatus());
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

    public String getFormatId1() {
        return formatId1;
    }

    public void setFormatId1(String formatId1) {
        this.formatId1 = formatId1;
    }

    public int getWeight1() {
        return weight1;
    }

    public void setWeight1(int weight1) {
        this.weight1 = weight1;
    }

    public String getFormatId2() {
        return formatId2;
    }

    public void setFormatId2(String formatId2) {
        this.formatId2 = formatId2;
    }

    public int getWeight2() {
        return weight2;
    }

    public void setWeight2(int weight2) {
        this.weight2 = weight2;
    }

    public VTypeEntity getParent() {
        return parent;
    }

    public void setParent(VTypeEntity parent) {
        this.parent = parent;
    }

    public boolean checkFormatIdParams() {
        if (this.getFormatId() == null || this.getFormatId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCreateParams() {
        if (this.getTypeId() == null || this.getTypeId().trim().equals("")){
            return false;
        }
        if (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2) {
            return false;
        }
        if (this.getLabel() <= 0 || this.getMeta() == null || this.getMeta().trim().equals("")
                || this.getAmount() <= 0 || this.getAmountMeta() == null || this.getAmountMeta().trim().equals("")
                || this.getPrice() <= 0 || this.getPriceMeta() == null || this.getPriceMeta().trim().equals("")
                || this.getPostage() < 0 || this.getPostageMeta() == null || this.getPostageMeta().trim().equals("")
                ) {
            return false;
        }
        if (this.getPricingStatus() != 0 && this.getPricingStatus() != 1 && this.getPricingStatus() != 2) {
            return false;
        }
        if (this.getPricingStatus() == 2 && (this.getPricingDiscount() <= 0 || this.getPricing() <= 0)) {
            return false;
        }
        if (this.getExpressStatus() != 0 && this.getExpressStatus() != 1 && this.getExpressStatus() != 2) {
            return false;
        }
        if (this.getExpressStatus() == 2 && (this.getExpressCount() <= 0)) {
            return false;
        }

        return true;
    }

    public boolean checkUpdateParams() {
        if (!checkCreateParams()) {
            return false;
        }
        if (this.getFormatId() == null && this.getFormatId().trim().equals("")) {
            return false;
        }
        return true;
    }

    public boolean checkMarkParams() {
        if (this.getFormatId() == null && this.getFormatId().trim().equals("")) {
            return false;
        }
        if (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2) {
            return false;
        }
        return true;
    }

    public boolean checkSwapParams() {
        if (this.getFormatId1() == null || this.getFormatId1().trim().equals("")
                || this.getFormatId2() == null || this.getFormatId2().trim().equals("")
                || this.getFormatId1().equals(this.getFormatId2())
                || this.getWeight1() == this.getWeight2()) {
            return false;
        } else {
            return true;
        }
    }
}
