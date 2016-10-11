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
     * 用户接口
     * 根据类型的ID读取规格的集合
     */
    @Clear(Session.class)
    void retrieves();

    /**
     * 用户接口
     * 根据规格的ID读取规格的详情
     */
    @Clear(Session.class)
    void retrieve();

    /**
     * 用户接口
     * 根据规格的ID读取产品树
     */
    @Clear(Session.class)
    void retrieveTree();

    /**
     * 用户接口
     * 根据规格ID读取倒置产品树
     */
    @Clear(Session.class)
    void retrieveTreeInversion();

    /**
     * 用户接口
     * 读取推荐列表
     */
    @Clear(Session.class)
    void recommends();

    /**
     * 管理员接口
     * 创建新的规格
     */
    void mCreate();

    /**
     * 管理员接口
     * 更新规格信息
     */
    void mUpdate();

    /**
     * 管理员接口
     * 状态更新规格状态
     */
    void mMark();

    /**
     * 管理员接口
     * 标记权重
     */
    void mKingWeight();

    /**
     * 管理员接口
     * 交换权重
     */
    void mSwapWeight();

    /**
     * 管理员接口
     * 读取权重列表
     */
    void mWeights();

    /**
     * 管理员接口
     * 根据类型的ID读取类型下的规格
     */
    void mRetrieves();

    /**
     * 管理员接口
     * 根据规格的ID读取规格的详情
     */
    void mRetrieve();

}
