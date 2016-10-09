package cn.foodslab.service.receiver;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-31 14:34.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IReceiverService {

    /**
     * 用户接口
     * 读取用户名下的所有状态正常的收货人信息
     * @param userId 用户ID
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> retrieveByUserId(String userId);

    /**
     * TODO 待处理接口，目前被查询接口调用，应该处理为全文检索
     * @param receiverId
     * @return
     */
    ReceiverEntity retrieveById(String receiverId);

    /**
     * 用户接口
     * 创建一个收货人
     * @param receiverEntity 收货人对象
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity create(ReceiverEntity receiverEntity);

    /**
     * 用户接口
     * 更新一个收货人，更新方式为保留其ID、accountId、status、createTime，更新其余所有属性。
     * @param receiverEntity 收货人对象
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity updateById(ReceiverEntity receiverEntity);

    /**
     * 用户接口
     * 删除一个收货人，删除方式为将其status变更为-1
     * @param receiverId 收货人的ID
     * @return fail null success 返回收货人对象
     */
    ReceiverEntity deleteById(String receiverId);

    /**
     * 用户接口
     * 设置用户名下的一个收货人为默认收货人，设置方式为将其用户名下的status重置为1，然后将当前收货人status设置为2
     * @param receiverId 当前收货人ID
     * @param userId 当前用户的ID
     * @return fail null success 返回要设置的收货人对象
     */
    ReceiverEntity kingReceiverInUser(String receiverId,String userId);

    /**
     * 管理员接口
     * 在后台读取用户名下的收货人信息
     * @param userId 用户ID
     * @return fail null success 收货人信息集合
     */
    LinkedList<ReceiverEntity> mRetrieveByUserId(String userId);

}
