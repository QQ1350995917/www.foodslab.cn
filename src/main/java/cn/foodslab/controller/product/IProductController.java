package cn.foodslab.controller.product;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品管理流程控制层
 * 该控制器对外进行流程控制，以及提供API服务
 * 名词释义：产品树 产品的数据结构是树形数据结构，产品树指的是产品的树形数据结构
 */
public interface IProductController {

    /**
     * 默认接口
     * 导向404
     */
    void index();

    /**
     * 后台接口
     * 读取所有产品树信息
     */
    void mRetrieves();

    /**
     * 后台接口
     * 根据系列ID读取一棵产品树信息
     */
    void mRetrieveBys();

    /**
     * 后台接口
     * 根据类型ID读取一棵产品树信息
     */
    void mRetrieveByt();

    /**
     * 后台接口
     * 根据规格ID读取一棵产品树信息
     */
    void mRetrieveByf();

    /**
     * 前台接口
     * 根据规格ID读取一棵产品树
     */
    void retrieveBys();

    /**
     * 前台接口
     * 根据类型ID读取一棵产品树
     */
    void retrieveByt();

    /**
     * 前台接口
     * 根据规格ID读取一棵产品树
     */
    void retrieveByf();

    /**
     * 前台接口
     * 获取所有的推荐产品
     */
    void recommend();

}
