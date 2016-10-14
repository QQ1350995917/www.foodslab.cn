package cn.foodslab.controller.receiver;

import cn.foodslab.interceptor.Session;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-08-31 14:35.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 收货人接口，所有的接口都在登录状态下执行
 */
@Before(Session.class)
public interface IReceiverController {

    /**
     * 导向404
     */
    void index();

    /**
     * 用户接口
     * 读取收货人
     * 该接口由登录用户调用，读取其用户名下的收货人信息
     */
    void retrieves();

    /**
     * 用户接口
     * 创建收货人
     * 该接口由登录用户调用，创建一个其账户名下的收货人信息
     * 参数详情见接口文档receiver
     */
    void create();

    /**
     * 用户接口
     * 更新收货人
     * 该接口由登录用户调用，更新一个其账户名下的收货人信息
     */
    void update();

    /**
     * 用户接口
     * 删除收货人
     * 该接口由登录用户调用，删除一个其账户名下的收货人信息
     */
    void delete();

    /**
     * 用户接口
     * 设置默认收货人
     * 该接口由登录用户调用，标注其用户的某个收货人信息为默认收货人信息
     */
    void king();

    /**
     * 管理员接口
     * 读取用户名下的收货人地址
     * 该接口由被授权的管理员调用
     */
    void mRetrieveByUser();

}
