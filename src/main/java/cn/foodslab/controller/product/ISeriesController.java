package cn.foodslab.controller.product;

/**
 * Created by Pengwei Ding on 2016-09-22 16:15.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品系列控制层接口
 * 使用m标记的接口为后台接口，需要检测相关权限才能访问数据层接口
 */
public interface ISeriesController {
    /**
     * 导向404
     */
    void index();

    /**
     * 用户接口
     * 读取系列进行显示
     * 应用于首页，系列等页面
     */
    void retrieves();

    /**
     * 用户接口
     * 根据系列的ID读取产品树信息
     */
    void tree();

    /**
     * 用户接口
     * 根据系列的ID读取倒置的产品树信息
     */
    void treeInversion();

    /**
     * 管理员接口
     * 创建新系列
     */
    void mCreate();

    /**
     * 管理员接口接口
     * 更新系列名称
     */
    void mUpdate();

    /**
     * 管理员接口接口
     * 更新系列状态
     */
    void mMark();

    /**
     * 管理员接口
     * 读取系列集合
     */
    void mRetrieves();
}
