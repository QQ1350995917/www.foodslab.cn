package cn.foodslab.model.cart;

import cn.foodslab.model.product.VFormatEntity;
import cn.foodslab.service.cart.CartEntity;

/**
 * Created by Pengwei Ding on 2016-09-26 15:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VCartEntity {

    private String sessionId;
    private String mappingId;
    private String formatId;
    private int amount;
    private int status = 1;
    private VFormatEntity formatEntity;

    public VCartEntity() {
        super();
    }

    public VCartEntity(CartEntity cartEntity) {
        this.mappingId = cartEntity.getMappingId();
        this.formatId = cartEntity.getFormatId();
        this.amount = cartEntity.getAmount();
    }

    public VCartEntity(String sessionId, String mappingId, String formatId, int amount, int status) {
        this.sessionId = sessionId;
        this.mappingId = mappingId;
        this.formatId = formatId;
        this.amount = amount;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public VFormatEntity getFormatEntity() {
        return formatEntity;
    }

    public void setFormatEntity(VFormatEntity formatEntity) {
        this.formatEntity = formatEntity;
    }

    public CartEntity getCartEntity(){
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMappingId(this.getMappingId());
        cartEntity.setFormatId(this.getFormatId());
        cartEntity.setAmount(this.getAmount());
        cartEntity.setStatus(this.getStatus());
        return cartEntity;
    }
}
