package cn.foodslab.service.user;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-29 18:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IUserServices {

    LinkedList<UserEntity> mRetrieve();

    UserEntity mRetrieveById(String userId);

    UserEntity mBlock(UserEntity userEntity);

    UserEntity mUnBlock(UserEntity userEntity);

}
