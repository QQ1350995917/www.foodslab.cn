package cn.foodslab.service.receiver;

import cn.foodslab.service.user.UserEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IReceiverService {

    LinkedList<ReceiverEntity> retrieve(UserEntity userEntity);

    ReceiverEntity retrieveById(String receiverId);

    ReceiverEntity create(ReceiverEntity receiverEntity);

    ReceiverEntity update(ReceiverEntity receiverEntity);

    boolean deleteById(String accountId,String receiverId);

}
