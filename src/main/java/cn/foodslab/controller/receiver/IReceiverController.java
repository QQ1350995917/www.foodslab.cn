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
     * 读取收货人
     */
    void retrieve();

    /**
     * 创建收货人
     */
    void create();

    /**
     * 更新收货人
     */
    void update();

    /**
     * 删除收货人
     */
    void delete();

    /**
     * 设置默认收货人
     */
    void king();

}
