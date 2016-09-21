package cn.foodslab.service.user;

/**
 * Created by Pengwei Ding on 2016-08-16 08:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AccountEntity {
    private String accountId;
    private String identity;
    private String name;
    private int gender;
    private String address;
    private String portrait;
    private String birthday;
    private int source;
    private String userId;
    private String createTime;
    private String updateTime;

    public AccountEntity() {
        super();
    }

    public AccountEntity(String accountId, String telephone, String name, int gender, String address, String portrait, String birthday, int source, String userId) {
        this.accountId = accountId;
        this.identity = telephone;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.portrait = portrait;
        this.birthday = birthday;
        this.source = source;
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTelephone() {
        return identity;
    }

    public void setTelephone(String telephone) {
        this.identity = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
