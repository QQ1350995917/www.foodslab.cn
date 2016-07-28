package cn.foodslab.back;

import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-27 17:59.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 后台管理页面控制器
 */
public class FrameController extends Controller {


    public void index() {
        this.render("/webapp/back-end/src/main/frame.html");
    }

    public void initView() {

    }

    public void menus() {
    }

}
