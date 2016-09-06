package cn.foodslab;

import cn.foodslab.cart.CartController;
import cn.foodslab.common.meta.MetaController;
import cn.foodslab.link.LinkController;
import cn.foodslab.manager.ManagerController;
import cn.foodslab.menu.MenuController;
import cn.foodslab.order.OrderController;
import cn.foodslab.page.PageController;
import cn.foodslab.poster.PosterController;
import cn.foodslab.product.ProductController;
import cn.foodslab.receiver.ReceiverController;
import cn.foodslab.user.UserController;
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

    static {
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
        me.setViewType(ViewType.FREE_MARKER);
        me.setFreeMarkerViewExtension(".html");
        me.setEncoding("utf-8");
        me.setError401View("");
        me.setError403View("");
        me.setError404View("");
        me.setError500View("");
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new Interceptor() {
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
        me.add("/", PageController.class);
        me.add("/meta", MetaController.class);
        me.add("/menus", MenuController.class);
        me.add("/manager", ManagerController.class);
        me.add("/product", ProductController.class);
        me.add("/link", LinkController.class);
        me.add("/poster", PosterController.class);
        me.add("/user", UserController.class);
        me.add("/cart", CartController.class);
        me.add("/order", OrderController.class);
        me.add("/receiver", ReceiverController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin(url, username, password, driver);
        me.add(c3p0Plugin);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.setShowSql(true);
        me.add(activeRecordPlugin);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
    }

    @Override
    public void beforeJFinalStop() {
        super.beforeJFinalStop();
    }
}

