package cn.foodslab.controller.cart;

import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.service.cart.CartEntity;

/**
 * Created by Pengwei Ding on 2016-09-26 15:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VCartEntity extends CartEntity {
    private String cs;
    private String[] productIds;//支持一个或多个的mappingIds查找
    private VFormatEntity formatEntity;

    public VCartEntity() {
        super();
    }

    public VCartEntity(CartEntity cartEntity) {
        this.setMappingId(cartEntity.getMappingId());
        ;
        this.setFormatId(cartEntity.getFormatId());
        ;
        this.setAmount(cartEntity.getAmount());
        ;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String[] getProductIds() {
        return productIds;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public VFormatEntity getFormatEntity() {
        return formatEntity;
    }

    public void setFormatEntity(VFormatEntity formatEntity) {
        this.formatEntity = formatEntity;
    }

    public boolean checkCreateParams() {
        if (this.getFormatId() == null || this.getFormatId().trim().equals("")
                || this.getAmount() < 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUpdateParams() {
        if (this.getMappingId() == null || this.getMappingId().trim().equals("")
                || this.getAmount() < 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkDeleteParams() {
        if (this.getMappingId() == null || this.getMappingId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
