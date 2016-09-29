package cn.foodslab.model.user;

import cn.foodslab.service.user.AccountEntity;

/**
 * Created by Pengwei Ding on 2016-09-29 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VAccountEntity {

    private String sessionId;
    private String identity;
    private String name;
    private int gender;
    private String address;
    private String portrait;
    private String birthday;
    private int source;
    private String userId;

    public VAccountEntity() {
        super();
    }

    public VAccountEntity(AccountEntity accountEntity) {
        this.identity = accountEntity.getIdentity();
        this.name = accountEntity.getName();
        this.gender = accountEntity.getGender();
        this.address = accountEntity.getAddress();
        this.portrait = accountEntity.getPortrait();
        this.birthday = accountEntity.getBirthday();
        this.source = accountEntity.getSource();
        this.userId = accountEntity.getUserId();
    }

    public VAccountEntity(String accountId, String identity, String name, int gender, String address, String portrait, String birthday, int source, String userId) {
        this.identity = identity;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.portrait = portrait;
        this.birthday = birthday;
        this.source = source;
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public AccountEntity getAccountEntity(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setIdentity(this.getIdentity());
        accountEntity.setName(this.getName());
        accountEntity.setGender(this.getGender());
        accountEntity.setAddress(this.getAddress());
        accountEntity.setPortrait(this.getPortrait());
        accountEntity.setBirthday(this.getBirthday());
        accountEntity.setSource(this.getSource());
        accountEntity.setUserId(this.getUserId());
        return  accountEntity;
    }
}

















