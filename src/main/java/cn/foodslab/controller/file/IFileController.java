package cn.foodslab.controller.file;

import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.MenuInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;

/**
 * Created by Pengwei Ding on 2016-11-01 15:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFileController {
    void index();

    /**
     * 管理员接口
     * 管理员上传类型图片
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mTypeCover();

    /**
     * 管理员接口
     * 管理员上传类型介绍图片
     */
    @Before({SessionInterceptor.class, ManagerInterceptor.class, MenuInterceptor.class})
    void mTypeDirectionImage();
}
