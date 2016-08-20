package cn.foodslab.front.user;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IUserServices {

    /**
     * 创建账户
     * 同时创建用户ID
     * @param accountEntity
     * @return
     */
    IResultSet createAccount(AccountEntity accountEntity);

    /**
     * 更新账户
     * @param accountEntity
     * @return
     */
    IResultSet updateAccount(AccountEntity accountEntity);

    /**
     * 绑定账户
     * 把账户绑定到用户ID
     * @param userEntity
     * @param accountEntity
     * @return
     */
    IResultSet bindAccount(UserEntity userEntity,AccountEntity accountEntity);

    IResultSet createReceiver(AccountEntity accountEntity,ReceiverEntity receiverEntity);

    IResultSet updateReceiver(ReceiverEntity receiverEntity);

    IResultSet retrieveAccount(UserEntity userEntity);

    IResultSet retrieveReceiver(AccountEntity accountEntity);
}
