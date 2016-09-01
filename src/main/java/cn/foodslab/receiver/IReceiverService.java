package cn.foodslab.receiver;

import cn.foodslab.common.response.IResultSet;

/**
 * Created by Pengwei Ding on 2016-08-31 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IReceiverService {
    IResultSet create(ReceiverEntity receiverEntity);
    IResultSet retrieve(String receiverId);
}
