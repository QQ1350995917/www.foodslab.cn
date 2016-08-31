package cn.foodslab.manager;

/**
 * Created by Pengwei Ding on 2016-07-30 10:03.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员流程控制层
 * 该控制器对外进行流程控制，以及提供API服务
 */
public interface IManagerController {

    /**
     * 读取管理员列表
     */
    void index();

    /**
     * 管理员用户名是否可用
     */
    void check();

    /**
     * 创建管理员
     */
    void create();

    /**
     * 更新管理员
     */
    void update();

}
