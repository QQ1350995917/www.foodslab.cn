package cn.foodslab.service.user;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountServices {

    /**
     * 通过电话号码读取账户
     * @param phoneNumber
     * @return 返回查询到的账户或者null
     */
    AccountEntity retrieve(String phoneNumber);

    /**
     * 通过用户ID读取其下的所有账户
     * @param userId
     * @return 返回查询到的账户或者空队列
     */
    LinkedList<AccountEntity> retrieveAccountsByUserId(String userId);

    /**
     * 通过账户ID读取用户信息
     * @param account
     * @return 返回查询到的用户或null
     */
    UserEntity retrieveUserByAccountId(String account);

    /**
     * 创建新的账户同时创建用户ID
     * @param accountEntity
     * @return 返回新建的账户或者null
     */
    AccountEntity create(AccountEntity accountEntity);

    /**
     * 把两个账户绑定到同一个用户
     * @param accountEntity1
     * @param accountEntity2
     * @return 返回绑定到同一个用户下的账户的集合或者null
     */
    LinkedList<AccountEntity> bind(AccountEntity accountEntity1,AccountEntity accountEntity2);

    /**
     * 更新账户信息
     * @param accountEntity
     * @return 返回更新成功的账户或者null
     */
    AccountEntity update(AccountEntity accountEntity);


}
