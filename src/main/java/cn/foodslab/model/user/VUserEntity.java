package cn.foodslab.model.user;

import cn.foodslab.service.user.UserEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-29 17:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VUserEntity {
    private String userId;
    private int status;
    private LinkedList<VAccountEntity> accountEntities;

    public VUserEntity() {
        super();
    }

    public VUserEntity(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.status = userEntity.getStatus();
    }

    public VUserEntity(String userId, int status) {
        this.userId = userId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LinkedList<VAccountEntity> getAccountEntities() {
        return accountEntities;
    }

    public void setAccountEntities(LinkedList<VAccountEntity> accountEntities) {
        this.accountEntities = accountEntities;
    }

    public UserEntity getUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(this.getUserId());
        userEntity.setStatus(this.getStatus());
        return userEntity;
    }
}
