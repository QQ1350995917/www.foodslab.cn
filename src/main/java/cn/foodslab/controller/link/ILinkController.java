package cn.foodslab.controller.link;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ILinkController {

    /**
     * 404
     */
    void index();

    /**
     * 用户接口
     * 读取链接树
     */
    void retrieves();

    /**
     * 管理员接口
     * 创建链接分类
     */
    void mCreate();

    /**
     * 管理员接口
     * 更新链接分类
     */
    void mUpdate();

    /**
     * 管理员接口
     * 更新链接分类状态
     */
    void mMark();

    /**
     * 管理员接口
     * 交换链接分类的顺序
     */
    void mSwap();

    /**
     * 管理员接口
     * 读取链接集合
     */
    void mRetrieves();

}
