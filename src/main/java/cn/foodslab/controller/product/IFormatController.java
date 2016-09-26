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
    void mCreate();

    /**
     * 更新
     */
    void mUpdate();

    /**
     * 状态
     */
    void mMark();

    /**
     * 读取
     */
    void mRetrieves();

    /**
     * 读取
     */
    @Clear(Session.class)
    void retrieves();

    /**
     * 标记权重
     */
    void mKingWeight();

    /**
     * 交换权重
     */
    void mSwapWeight();

    /**
     * 读取权重列表
     */
    void mWeights();

    /**
     * 读取推荐列表
     */
    void recommends();
}
