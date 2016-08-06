package cn.foodslab.back.product;

/**
 * Created by Pengwei Ding on 2016-07-30 21:28.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 产品管理流程控制层
 * 该控制器对外进行流程控制，以及提供API服务
 */
public interface IProductController {

    /**
     * 首页获取整个产品树数据
     */
    void index();

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
     * 创建规格
     */
    void createFormat();

    void update();
}
