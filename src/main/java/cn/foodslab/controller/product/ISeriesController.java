package cn.foodslab.controller.product;

import cn.foodslab.interceptor.Session;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;

/**
 * Created by Pengwei Ding on 2016-09-22 16:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品系列控制层接口
 * 使用m标记的接口为后台接口，需要检测相关权限才能访问数据层接口
 */
@Before(Session.class)
public interface ISeriesController {
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
     * 状态
     */
    void mMark();

    /**
     * 后台接口
     * 读取
     */
    void mRetrieves();

    /**
     * 前台接口
     * 读取系列进行显示
     * 应用于首页，系列等页面
     */
    @Clear(Session.class)
    void retrieves();

    /**
     * 前台接口
     * 读取倒置的产品树数据
     * 应用于系列页面页签切换的数据显示
     */
    void retrieveInversionTree();

}
