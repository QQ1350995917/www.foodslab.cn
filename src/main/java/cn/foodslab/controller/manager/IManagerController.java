package cn.foodslab.controller.manager;

/**
 * Created by Pengwei Ding on 2016-07-30 10:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员流程控制层
 * 该控制器对外进行流程控制，以及提供API服务
 */
public interface IManagerController {

    /**
     * 404
     */
    void index();

    /**
     * 管理员接口
     * 管理员登录
     */
    void retrieve();

    /**
     * 管理员接口
     * 更新密码
     */
    void password();

    /**
     * 系统管理员接口
     * 读取管理员列表
     */
    void mRetrieves();

    /**
     * 系统管理员接口
     * 创建管理员
     */
    void mCreate();

    /**
     * 系统管理员
     * 重置管理员密码
     */
    void mUpdate();

    /**
     * 系统管理员
     * 标记管理员转台
     */
    void mMark();

}
