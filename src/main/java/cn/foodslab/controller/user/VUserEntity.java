package cn.foodslab.controller.user;

import cn.foodslab.service.user.UserEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-29 17:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VUserEntity extends UserEntity {
    private String sessionId;
    private LinkedList<VAccountEntity> children;

    public VUserEntity() {
        super();
    }

    public VUserEntity(UserEntity userEntity) {
        this.setUserId(userEntity.getUserId());
        this.setStatus(userEntity.getStatus());
    }

    public VUserEntity(String userId, int status) {
        this.setUserId(userId);
        this.setStatus(status);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LinkedList<VAccountEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VAccountEntity> children) {
        this.children = children;
    }

}
