package cn.foodslab.controller.product;

import cn.foodslab.interceptor.Session;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

/**
 * Created by Pengwei Ding on 2016-09-22 16:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
@Before(Session.class)
public interface IFormatController {
    /**
     * 导向404
     */
    void index();

    /**
     * 创建
     */
    void create();

    /**
     * 更新
     */
    void update();

    /**
     * 状态
     */
    void mark();

    /**
     * 读取
     */
    @Clear(Session.class)
    void retrieve();
}
