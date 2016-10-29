package cn.foodslab.controller.user;

import cn.foodslab.common.cache.ISessionEntity;
import cn.foodslab.service.user.UserEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-29 17:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VUserEntity extends UserEntity implements ISessionEntity {
    private String cs;
    private LinkedList<VAccountEntity> children;

    public VUserEntity() {
        super();
    }

    public VUserEntity(String cs, String userId, LinkedList<VAccountEntity> children) {
        this.cs = cs;
        this.setUserId(userId);
        this.children = children;
    }

    public VUserEntity(String cs) {
        this.cs = cs;
    }

    public VUserEntity(UserEntity userEntity) {
        this.setUserId(userEntity.getUserId());
        this.setStatus(userEntity.getStatus());
    }


    public VUserEntity(String userId, int status) {
        this.setUserId(userId);
        this.setStatus(status);
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public LinkedList<VAccountEntity> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<VAccountEntity> children) {
        this.children = children;
    }

    public boolean checkUserIdParams() {
        if (this.getUserId() == null || this.getUserId().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkMarkParams() {
        if (this.getUserId() == null || this.getUserId().trim().equals("")
                || (this.getStatus() != -1 && this.getStatus() != 1 && this.getStatus() != 2)) {
            return false;
        } else {
            return true;
        }
    }

}
