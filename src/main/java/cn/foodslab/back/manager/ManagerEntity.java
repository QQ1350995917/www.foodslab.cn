package cn.foodslab.back.manager;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-28 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员对象。为了便利性考虑暂时融合可控制层数据对象和持久层的数据对象
 * TODO: 可以考虑拆分
 */
public class ManagerEntity  {
    private String managerId; // 数据ID
    private String username; // 用户名，即登录名
    private boolean isUsernameU = false; // 非数据库字段，标记是否要更新数据库字段，默认为false不更新，非false更新
    private String password; // 用户密码，即登录密码，非明文 TODO: 采用哪种加密方式
    private boolean isPasswordU = false; // 非数据库字段，标记是否要更新数据库字段，默认为false不更新，非false更新
    private int level;
    private int queue;
    private int status; // 数据状态，可以查询数据库元数据表，这里取值有3个，-1标示删除，0标示禁用，1标示正常
    private boolean isStatusU = false; // 非数据库字段，标记是否要更新数据库字段，默认为false不更新，非false更新
    private String pId;
    private String createTime;
    private String updateTime;
    private LinkedList<ManagerMenuEntity> managerMenuEntitiesMapping;
    private boolean isManagerMenusMappingU = false; // 非数据库字段，标记是否要更新数据库字段，默认为false不更新，非false更新

    public ManagerEntity() {
    }

    public ManagerEntity(String managerId) {
        this.managerId = managerId;
    }

    /**
     * 更新用户名
     * @param managerId
     * @param username
     */
    public ManagerEntity(String managerId, String username) {
        this.managerId = managerId;
        this.username = username;
    }

    /**
     * 更新状态
     * @param managerId
     * @param status
     */
    public ManagerEntity(String managerId,int status) {
        this.managerId = managerId;
        this.status = status;
    }


    /**
     * 更新密码
     * @param managerId
     * @param username
     */
    public ManagerEntity(String managerId, String username,String password) {
        this.managerId = managerId;
        this.username = username;
        this.password = password;
    }

    /**
     * 创建用户
     * @param managerId
     * @param username
     * @param password
     * @param level
     * @param queue
     * @param status
     * @param pId
     */
    public ManagerEntity(String managerId, String username, String password, int level, int queue, int status, String pId) {
        this.managerId = managerId;
        this.username = username;
        this.password = password;
        this.level = level;
        this.queue = queue;
        this.status = status;
        this.pId = pId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isUsernameU() {
        return isUsernameU;
    }

    public void setIsUsernameU(boolean isUsernameU) {
        this.isUsernameU = isUsernameU;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordU() {
        return isPasswordU;
    }

    public void setIsPasswordU(boolean isPasswordU) {
        this.isPasswordU = isPasswordU;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isStatusU() {
        return isStatusU;
    }

    public void setIsStatusU(boolean isStatusU) {
        this.isStatusU = isStatusU;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public LinkedList<ManagerMenuEntity> getManagerMenuEntitiesMapping() {
        return managerMenuEntitiesMapping;
    }

    public void setManagerMenuEntitiesMapping(LinkedList<ManagerMenuEntity> managerMenuEntitiesMapping) {
        this.managerMenuEntitiesMapping = managerMenuEntitiesMapping;
    }

    public boolean isManagerMenusMappingU() {
        return isManagerMenusMappingU;
    }

    public void setIsManagerMenusMappingU(boolean isManagerMenusMappingU) {
        this.isManagerMenusMappingU = isManagerMenusMappingU;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toInsterSQLString(){
        return "INSERT INTO manager (managerId,username,password,level,queue,status,pid) " +
                "VALUES('"+ this.getManagerId() +"','"+ this.getUsername()+"','"+this.getPassword()+"','"+this.getLevel()+"','"+this.getQueue()+"','"+this.getStatus()+"','"+this.getpId()+"');";
    }
}
