package cn.foodslab.manager;

/**
 * Created by Pengwei Ding on 2016-07-29 13:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ManagerMenuEntity {

    private String managerId;
    private String menuId;
    private String menuLabel;
    private String createTime;
    private String updateTime;

    public ManagerMenuEntity() {
    }

    public ManagerMenuEntity(String managerId, String menuId, String menuLabel) {
        this.managerId = managerId;
        this.menuId = menuId;
        this.menuLabel = menuLabel;
    }

    public ManagerMenuEntity(String managerId, String menuId, String menuLabel, String createTime, String updateTime) {
        this.managerId = managerId;
        this.menuId = menuId;
        this.menuLabel = menuLabel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
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

    @Override
    public boolean equals(Object obj) {
        ManagerMenuEntity manMenuEntity = (ManagerMenuEntity) obj;
        if (manMenuEntity.getManagerId().equals(this.getManagerId()) && manMenuEntity.getMenuId().equals(this.getMenuId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
