package cn.foodslab.controller.user;

import cn.foodslab.service.user.AccountEntity;

/**
 * Created by Pengwei Ding on 2016-09-29 17:45.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VAccountEntity extends AccountEntity {
    public VAccountEntity() {
        super();
    }

    public VAccountEntity(String accountId,String identity,int source,String userId) {
        this.setAccountId(accountId);
        this.setIdentity(identity);
        this.setSource(source);
        this.setUserId(userId);
    }

    public VAccountEntity(AccountEntity accountEntity) {
        this.setAccountId(accountEntity.getAccountId());
        this.setIdentity(accountEntity.getIdentity());
        this.setNickName(accountEntity.getNickName());
        this.setGender(accountEntity.getGender());
        this.setAddress(accountEntity.getAddress());
        this.setPortrait(accountEntity.getPortrait());
        this.setBirthday(accountEntity.getBirthday());
        this.setSource(accountEntity.getSource());
        this.setUserId(accountEntity.getUserId());
    }
}
