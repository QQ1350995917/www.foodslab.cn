package cn.foodslab.controller.manager;

import cn.foodslab.common.cache.ISessionEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.service.manager.ManagerEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-10-17 18:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VManagerEntity extends ManagerEntity implements ISessionEntity {
    private String cs;
    private LinkedList<VMenuEntity> menus;
    private VUserEntity user;

    public VManagerEntity() {
        super();
    }

    public VManagerEntity(String cs) {
        this.cs = cs;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public LinkedList<VMenuEntity> getMenus() {
        return menus;
    }

    public void setMenus(LinkedList<VMenuEntity> menus) {
        this.menus = menus;
    }

    public VUserEntity getUser() {
        return user;
    }

    public void setUser(VUserEntity user) {
        this.user = user;
    }
}
