package cn.foodslab.controller.manager;

import cn.foodslab.common.cache.ISessionEntity;
import cn.foodslab.controller.menu.VMenuEntity;
import cn.foodslab.controller.user.VUserEntity;
import cn.foodslab.service.manager.ManagerEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-10-17 18:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VManagerEntity extends ManagerEntity implements ISessionEntity {
    private String cs;//驻留session的内容
    private LinkedList<String> actionKeys;//驻留session的内容
    private LinkedList<VMenuEntity> menus;
    private VUserEntity user;

    public VManagerEntity() {
        super();
    }

    public VManagerEntity(String mcs) {
        this.cs = mcs;
    }

    public VManagerEntity(ManagerEntity managerEntity) {
        this.setManagerId(managerEntity.getManagerId());
        this.setLoginName(managerEntity.getLoginName());
        this.setUsername(managerEntity.getUsername());
        this.setPassword(managerEntity.getPassword());
        this.setLevel(managerEntity.getLevel());
        this.setQueue(managerEntity.getQueue());
        this.setStatus(managerEntity.getStatus());
        this.setCreateTime(managerEntity.getCreateTime());
        this.setUpdateTime(managerEntity.getUpdateTime());
        if (managerEntity instanceof VManagerEntity) {
            LinkedList<VMenuEntity> menus = ((VManagerEntity) managerEntity).getMenus();
            this.setMenus(menus);
        }
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public LinkedList<String> getActionKeys() {
        return actionKeys;
    }

    public void setActionKeys(LinkedList<String> actionKeys) {
        this.actionKeys = actionKeys;
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

    /**
     * 检测登录参数是否正确
     * @return success true or fail false
     */
    public boolean checkLoginParams() {
        if (this.getLoginName() == null
                || this.getPassword() == null
                || this.getLoginName().trim().equals("")
                || this.getPassword().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测session参数是否正确
     * @return success true or fail false
     */
    public boolean checkCSParams() {
        if (this.getCs() == null
                || this.getCs().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测修改用户信息参数是否正确
     * @return success true or fail false
     */
    public boolean checkUpdateParams() {
        if (this.getUsername() == null
                || this.getPassword() == null
                || this.getUsername().trim().equals("")
                || this.getPassword().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
