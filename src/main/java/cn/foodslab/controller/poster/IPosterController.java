package cn.foodslab.controller.poster;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IPosterController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 读取海报集合
     */
    void retrieves();

    /**
     * 管理员接口
     * 创建海报
     */
    void mCreate();

    /**
     * 管理员接口
     * 更新海报
     */
    void mUpdate();

    /**
     * 管理员接口
     * 更新海报状态
     */
    void mMark();

    /**
     * 管理员接口
     * 交换海报权重
     */
    void mSwap();

    /**
     * 管理员接口
     * 读取海报集合
     */
    void mRetrieves();
}
