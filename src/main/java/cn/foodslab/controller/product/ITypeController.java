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
     * 用户接口
     * 根据类型的ID读取类型的详情
     */
    @Clear(Session.class)
    void retrieve();

    /**
     * 管理员接口
     * 创建类型
     */
    void mCreate();

    /**
     * 管理员接口
     * 更新类型名称
     */
    void mUpdate();

    /**
     * 管理员接口
     * 更新状态
     */
    void mMark();

    /**
     * 管理员接口
     * 更新类型图片
     */
    void mImage();

    /**
     * 管理员接口
     * 删除类型图片
     */
    void mImageDelete();

    /**
     * 管理员接口
     * 更新简介
     */
    void mSummary();

    /**
     * 管理员接口
     * 更新说明
     */
    void mDirections();

    /**
     * 管理员接口
     * 根据产品系列的ID读取简略类型信息集合
     */
    void mRetrieves();

    /**
     * 管理员接口
     * 根据产品类型的ID读取类型详情
     */
    void mRetrieve();

}
