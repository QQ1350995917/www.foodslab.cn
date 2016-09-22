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
     */
    void index();

    /**
     * 获取产品所有系列（前台接口）
     * 该接口接受的参数是一个系列的ID或者零个参数
     * 1：当接口中有seriesId参数，表示获取所有系列的同时也获取指定系列下的产品树
     * 2：当接口中有没有参数，表示获取所有系列
     */
    void series();

    /**
     * 通过类型的ID获取产品树
     */
    void type();


    /**
     * 通过规格的ID获取产品树
     */
    void format();

    /**
     * 获取所有的推荐产品（前台接口）
     */
    void recommend();

    /**
     * 获取整个产品树（后台接口）
     */
    void retrieve();

    /**
     * 获取整个产品树的逆转数据
     */
    void convert();

    /**
     * 获取整个系列的数据
     */
    void retrieveSeries();

    /**
     * 获取整个型号的数据
     */
    void retrieveType();

    /**
     * 获取整个规格的数据
     */
    void retrieveFormat();

    /**
     * 创建系列
     */
    void createSeries();

    /**
     * 更新系列
     */
    void updateSeries();

    /**
     * 创建类型
     */
    void createType();

    /**
     * 更新类型的名称和状态
     */
    void updateType();

    /**
     * 更新类型的描述
     */
    void updateTypeDetail();

    /**
     * 创建规格
     */
    void createFormat();

    /**
     * 更新推荐位
     */
    void updateWeight();

    /**
     * 交换推荐位
     */
    void swapWeight();

}
