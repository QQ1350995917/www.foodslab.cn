package cn.foodslab;

import cn.foodslab.back.FrameController;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.render.ViewType;

/**
 * Created by Pengwei Ding on 2016-07-27 17:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 配置管理器
 */
public class ApplicationConfigure extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("utf-8");
        me.setViewType(ViewType.JSP);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("basePath"));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", FrameController.class);
    }

    @Override
    public void configPlugin(Plugins me) {

    }
}
