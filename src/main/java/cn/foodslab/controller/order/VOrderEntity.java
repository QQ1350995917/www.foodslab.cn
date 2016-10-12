package cn.foodslab.controller.order;

import cn.foodslab.controller.product.VFormatEntity;
import cn.foodslab.service.order.OrderEntity;
import cn.foodslab.service.receiver.ReceiverEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-28 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 支付页面提交订单的数据模型，兼容匿名订单和非匿名订单
 */
public class VOrderEntity implements Comparable {
    private String sessionId;
    private String orderId;//响应字段
    private String senderName;
    private String senderPhone;
    private String receiverId;
    private float cost;
    private float postage;
    private int status;
    private String expressLabel;
    private String expressNumber;
    private long createTime;
    private LinkedList<VFormatEntity> formatEntities;//响应字段

    private String productIds;

    /**
     * 兼容匿名订单*
     */
    private String province;
    private String city;
    private String county;
    private String town;
    private String village;
    private String append;
    private String name;
    private String phone0;
    private String phone1;

    public VOrderEntity() {
        super();
    }

    public VOrderEntity(OrderEntity orderEntity) {
        this.orderId = orderEntity.getOrderId();
        this.senderName = orderEntity.getSenderName();
        this.senderPhone = orderEntity.getSenderPhone();
        this.receiverId = orderEntity.getReceiverId();
        this.cost = orderEntity.getCost();
        this.postage = orderEntity.getPostage();
        this.status = orderEntity.getStatus();
        this.expressLabel = orderEntity.getExpressLabel();
        this.expressNumber = orderEntity.getExpressNumber();
        this.createTime = orderEntity.getCreateTime();
    }

    public VOrderEntity(String sessionId, String orderId, String senderName, String senderPhone, String receiverId, float cost, float postage, int status, String expressLabel, String expressNumber) {
        this.sessionId = sessionId;
        this.orderId = orderId;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.receiverId = receiverId;
        this.cost = cost;
        this.postage = postage;
        this.status = status;
        this.expressLabel = expressLabel;
        this.expressNumber = expressNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public LinkedList<VFormatEntity> getFormatEntities() {
        return formatEntities;
    }

    public void setFormatEntities(LinkedList<VFormatEntity> formatEntities) {
        this.formatEntities = formatEntities;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone0() {
        return phone0;
    }

    public void setPhone0(String phone0) {
        this.phone0 = phone0;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public OrderEntity getOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(this.getOrderId());
        orderEntity.setSenderName(this.getSenderName());
        orderEntity.setSenderPhone(this.getSenderPhone());
        orderEntity.setReceiverId(this.getReceiverId());
        orderEntity.setCost(this.getCost());
        orderEntity.setPostage(this.getPostage());
        orderEntity.setStatus(this.getStatus());
        orderEntity.setExpressLabel(this.getExpressLabel());
        orderEntity.setExpressNumber(this.getExpressNumber());
        return orderEntity;
    }

    public ReceiverEntity getReceiverEntity() {
        ReceiverEntity receiverEntity = new ReceiverEntity();
        receiverEntity.setName(this.getName());
        receiverEntity.setPhone0(this.getPhone0());
        receiverEntity.setPhone1(this.getPhone1());
        receiverEntity.setProvince(this.getProvince());
        receiverEntity.setCity(this.getCity());
        receiverEntity.setCounty(this.getCounty());
        receiverEntity.setTown(this.getTown());
        receiverEntity.setVillage(this.getVillage());
        receiverEntity.setAppend(this.getAppend());
        return receiverEntity;
    }

    @Override
    public int compareTo(Object o) {
        if (this.getCreateTime() > ((VOrderEntity) o).getCreateTime()) {
            return -1;
        } else {
            return 1;
        }
    }
}
