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
     * 后台接口
     * 创建
     */
    void mCreate();

    /**
     * 后台接口
     * 更新
     */
    void mUpdate();

    /**
     * 后台接口
     * 更新状态
     */
    void mMark();

    /**
     * 后台接口
     * 更新简介
     */
    void mSummary();

    /**
     * 后台接口
     * 更新说明
     */
    void mDirections();

    /**
     * 后台接口
     * 根据产品类型的ID读取
     */
    void mRetrieves();

    /**
     * 前台接口
     *
     */
    @Clear(Session.class)
    void retrieves();

    /**
     * 前台接口
     * 根据产品类型的ID读取
     * 详情页面使用了该接口
     */
    @Clear(Session.class)
    void retrieveTree();

}
