package cn.foodslab.controller.receiver;

import cn.foodslab.service.receiver.ReceiverEntity;

/**
 * Created by Pengwei Ding on 2016-09-21 18:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VReceiverEntity extends ReceiverEntity {
    private String sessionId;
    public VReceiverEntity() {
        super();
    }

    public VReceiverEntity(ReceiverEntity receiverEntity) {
        this.setReceiverId(receiverEntity.getReceiverId());
        this.setName(receiverEntity.getName());
        this.setPhone0(receiverEntity.getPhone0());
        this.setPhone1(receiverEntity.getPhone1());
        this.setProvince(receiverEntity.getProvince());
        this.setCity(receiverEntity.getCity());
        this.setCounty(receiverEntity.getCounty());
        this.setTown(receiverEntity.getTown());
        this.setVillage(receiverEntity.getVillage());
        this.setAppend(receiverEntity.getAppend());
        this.setStatus(receiverEntity.getStatus());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
