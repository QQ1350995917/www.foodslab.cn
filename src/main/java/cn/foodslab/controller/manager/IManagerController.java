package cn.foodslab.controller.manager;

import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

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
    void mLogin();

    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void mExit();

    /**
     * 管理员接口
     * 管理员信息读取
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void mRetrieve();

    /**
     * 管理员接口
     * 更新用户名和密码
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void mUpdate();

    /**
     * 系统管理员接口
     * 读取管理员列表
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void MRetrieves();

    /**
     * 系统管理员接口
     * 创建管理员
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void MCreate();

    /**
     * 系统管理员
     * 重置管理员密码
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void MUpdate();

    /**
     * 系统管理员
     * 标记管理员转台
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    void MMark();

}
