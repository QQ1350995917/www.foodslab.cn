package cn.foodslab.back.manager;

/**
 * Created by Pengwei Ding on 2016-07-28 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员对象
 */
public class ManagerEntity {
    private String managerId;
    private String username;
    private String password;
    private int level;
    private int queue;
    private int status;
    private String pId;
    private String createTime;
    private String updateTime;

    public ManagerEntity() {
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
