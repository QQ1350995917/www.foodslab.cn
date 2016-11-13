package cn.foodslab.controller.system;

import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.MenuInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-11-13 16:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface ISystemController {

    void index();

    /**
     * 管理员接口
     * 查看系统状态
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mStatus();

}
