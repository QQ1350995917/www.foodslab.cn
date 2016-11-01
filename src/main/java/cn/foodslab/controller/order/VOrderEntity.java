package cn.foodslab.controller.order;

import cn.foodslab.controller.cart.VCartEntity;
import cn.foodslab.controller.receiver.VReceiverEntity;
import cn.foodslab.service.order.OrderEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-28 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 支付页面提交订单的数据模型，兼容匿名订单和非匿名订单
 */
public class VOrderEntity extends OrderEntity implements Comparable {
    private String cs;
    private String[] productIds;//请求字段 匿名下单的产品ID或用户购物车中的产品映射IDs
    private VReceiverEntity receiver;//请求字段 兼容匿名订单中的收货人信息
    private LinkedList<VCartEntity> cartEntities;//响应字段

    public VOrderEntity() {
        super();
    }

    public VOrderEntity(OrderEntity orderEntity) {
        this.setOrderId(orderEntity.getOrderId());
        this.setSenderName(orderEntity.getSenderName());
        this.setSenderPhone(orderEntity.getSenderPhone());
        this.setReceiverId(orderEntity.getReceiverId());
        this.setCost(orderEntity.getCost());
        this.setPostage(orderEntity.getPostage());
        this.setStatus(orderEntity.getStatus());
        this.setExpressLabel(orderEntity.getExpressLabel());
        this.setExpressNumber(orderEntity.getExpressNumber());
        this.setCreateTime(orderEntity.getCreateTime());
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

    public VReceiverEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(VReceiverEntity receiver) {
        this.receiver = receiver;
    }

    public LinkedList<VCartEntity> getCartEntities() {
        return cartEntities;
    }

    public void setCartEntities(LinkedList<VCartEntity> cartEntities) {
        this.cartEntities = cartEntities;
    }

    @Override
    public int compareTo(Object o) {
        if (this.getCreateTime() > ((VOrderEntity) o).getCreateTime()) {
            return -1;
        } else {
            return 1;
        }
    }

    public boolean checkOrderIdParams() {
        if (this.getOrderId() == null || this.getOrderId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkStatusParams() {
        if (this.getStatus() != -1 && this.getStatus() != 0 && this.getStatus() != 1 && this.getStatus() != 2 && this.getStatus() != 3) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkExpressParams() {
        if (this.getExpressLabel() == null || this.getExpressLabel().trim().equals("")
                || this.getExpressNumber() == null || this.getExpressNumber().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
