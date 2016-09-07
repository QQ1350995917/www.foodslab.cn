package cn.foodslab.user;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IUserServices {


    /**
     * 读取账户
     * 管理员接口
     */
    IResultSet retrieve();

    /**
     * 创建新的账户
     * 同时创建用户ID
     * @param accountEntity
     */
    IResultSet create(AccountEntity accountEntity);

    /**
     * 更新账户

     */
    IResultSet update(AccountEntity accountEntity);

    /**
     * 禁用账户
     * 管理员接口
     * @param accountEntity
     */
    IResultSet block(AccountEntity accountEntity);

    /**
     * 绑定账户
     * 把账户绑定到用户ID
     * @param userEntity
     * @param accountEntity
     * @return
     */
    IResultSet bind(UserEntity userEntity,AccountEntity accountEntity);
}
