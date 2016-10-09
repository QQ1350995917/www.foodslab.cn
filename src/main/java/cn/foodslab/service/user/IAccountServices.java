package cn.foodslab.service.user;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountServices {

    /**
     * 用户接口
     * 用户注册、更换身份标记时候查看目标身份是否已经被注册
     * @param identity 目标身份，如电话号码，微信ID或QQ ID
     * @return false 目标可用，true 目标不可用
     */
    boolean existAccount(String identity);

    /**
     * 用户接口
     * 创建新的账户同时创建用户ID
     * @param accountEntity 账户对象
     * @return fail null success 账户对象
     */
    AccountEntity create(AccountEntity accountEntity);

    /**
     * 用户接口
     * 通过账户的身份ID读取账户
     * @param identity 账户身份ID
     * @return fail null success 账户对象
     */
    AccountEntity retrieve(String identity);

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
