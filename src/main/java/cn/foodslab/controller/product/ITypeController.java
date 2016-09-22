package cn.foodslab.controller.product;

import cn.foodslab.interceptor.Session;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

/**
 * Created by Pengwei Ding on 2016-09-22 16:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
@Before(Session.class)
public interface ITypeController {
    /**
     * 导向404
     */
    void index();

    /**
     * 创建
     */
    void mCreate();

    /**
     * 更新
     */
    void mUpdate();

    /**
     * 更新状态
     */
    void mMark();

    /**
     * 更新简介
     */
    void mSummary();

    /**
     * 更新说明
     */
    void mDirections();

    /**
     * 读取
     */
    @Clear(Session.class)
    void retrieve();

}
