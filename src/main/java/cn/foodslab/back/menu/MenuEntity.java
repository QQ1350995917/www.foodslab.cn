package cn.foodslab.back.menu;

/**
 * Created by Pengwei Ding on 2016-07-28 16:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理界面菜单对象
 */
public class MenuEntity {
    private String menuId; //ID
    private String label; //显示的名称
    private int level; //级别
    private int queue; // 顺序
    private String method; // 调用的方法的名称
    private String positionId; // 位置ID，标记在界面上所处的位置
    private String pId; // 父ID
    private int status; // -1 0 1 (-1删除状态，0，不显示状态，1正常状态，2该菜单除了超级管理员不可授予其他人)
    private String createTime;
    private String updateTime;

    public MenuEntity() {}

    public MenuEntity(String menuId, String label, int level, int queue, String method, String positionId, String pId, int status) {
        this.menuId = menuId;
        this.label = label;
        this.level = level;
        this.queue = queue;
        this.method = method;
        this.positionId = positionId;
        this.pId = pId;
        this.status = status;
    }

    public MenuEntity(String menuId, String label, int level, int queue, String method, String positionId, String pId, int status, String createTime, String updateTime) {
        this.menuId = menuId;
        this.label = label;
        this.level = level;
        this.queue = queue;
        this.method = method;
        this.positionId = positionId;
        this.pId = pId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        MenuEntity menuEntity = (MenuEntity) obj;
        if (menuEntity.getMenuId().equals(this.getMenuId())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toInsertSQLString(){
        return "INSERT INTO MENU (menuId,label,level,queue,method,positionId,pId,status) " +
                "VALUES('"+ this.getMenuId() +"','"+ this.getLabel()+"','"+this.getLevel()+"','"+this.getQueue()+"','"+this.getMethod()+"','"+this.getPositionId()+"','"+this.getpId()+"','"+this.getStatus()+"');";
    }
}
