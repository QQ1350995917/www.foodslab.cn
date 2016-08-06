package cn.foodslab;

import cn.foodslab.back.FrameController;
import cn.foodslab.back.manager.ManagerController;
import cn.foodslab.back.menu.MenuController;
import cn.foodslab.back.product.ProductController;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pengwei Ding on 2016-07-27 17:55.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 配置管理器
 */
public class ApplicationConfigure extends JFinalConfig {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    static{
        try {
            Properties prop = new Properties();
            prop.load(ApplicationConfigure.class.getClassLoader().getResourceAsStream("db.properties"));
            url = prop.getProperty("url");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            driver = prop.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("utf-8");
        me.setViewType(ViewType.JSP);
        me.setError401View("");
        me.setError403View("");
        me.setError404View("");
        me.setError500View("");
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new Interceptor(){
            @Override
            public void intercept(Invocation inv) {
                inv.getController().getResponse().setHeader("Access-Control-Allow-Origin", "*");
                inv.invoke();
            }
        });
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("basePath"));
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", FrameController.class);
        me.add("/menus",MenuController.class);
        me.add("/manager", ManagerController.class);
        me.add("/product",ProductController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(url, username, password,driver);
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.setShowSql(true);
        me.add(activeRecordPlugin);
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
    }
}

