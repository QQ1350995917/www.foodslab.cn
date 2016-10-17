package cn.foodslab.controller.page;

import cn.foodslab.interceptor.ManagerInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-27 17:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 后台管理页面控制器
 */
@Before(ManagerInterceptor.class)
public class FrameController extends Controller implements IFrameController{

    @Override
    public void index() {
        this.render("/webapp/widgets/frame.html");
    }
}
