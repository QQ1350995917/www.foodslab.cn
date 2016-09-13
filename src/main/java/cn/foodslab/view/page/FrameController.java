package cn.foodslab.view.page;

import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-27 17:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 后台管理页面控制器
 */
public class FrameController extends Controller implements IFrameController{

    @Override
    public void index() {
        this.render("/webapp/widgets/frame.html");
    }
}
