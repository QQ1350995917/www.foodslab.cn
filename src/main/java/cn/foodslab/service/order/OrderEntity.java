package cn.foodslab.service.order;

import cn.foodslab.service.product.FormatEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 13:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderEntity {
    private String orderId;
    private String accountId;
    private String senderName;
    private String senderPhone;
    private String receiverId;
    private float cost;
    private float postage;
    private int status;
    private String expressLabel;
    private String expressNumber;
    private String createTime;
    private String updateTime;
    private LinkedList<FormatEntity> products;

    public OrderEntity() {
        super();
    }

    public OrderEntity(String orderId, String accountId, String senderName, String senderPhone, String receiverId, float cost, float postage, int status, String expressLabel, String expressNumber, String createTime, String updateTime) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.receiverId = receiverId;
        this.cost = cost;
        this.postage = postage;
        this.status = status;
        this.expressLabel = expressLabel;
        this.expressNumber = expressNumber;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderEntity(String orderId, String accountId, String senderName, String senderPhone, String receiverId, float cost, float postage, int status) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.receiverId = receiverId;
        this.cost = cost;
        this.postage = postage;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExpressLabel() {
        return expressLabel;
    }

    public void setExpressLabel(String expressLabel) {
        this.expressLabel = expressLabel;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public LinkedList<FormatEntity> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<FormatEntity> products) {
        this.products = products;
    }
}
