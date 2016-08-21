package cn.foodslab.front;

import com.jfinal.core.Controller;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-08-19 16:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转均由该类实现的接口分发
 */
public class PageController extends Controller implements IPageController {

    @Override
    public void index() {
        this.setAttr("title", "食坊");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.css\">");
        this.setAttr("javaScripts", javaScripts);
        this.setAttr("styleSheets", styleSheets);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ps() {
        this.setAttr("title", "食坊-系列");
        String seriesId = this.getPara("seriesId");
        if (seriesId != null){
            this.setAttr("metaId","seriesId");
            this.setAttr("metaValue",seriesId);
        }
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/series.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pd() {
        this.setAttr("title", "食坊-详情");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pb() {
        this.setAttr("title", "食坊-支付");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pc() {
        this.setAttr("title", "食坊-购物车");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void po() {
        this.setAttr("title", "食坊-订单");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pe() {
        this.setAttr("title", "食坊");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pr() {
        this.setAttr("title", "食坊-预约");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pl() {
        this.setAttr("title", "食坊-订单查询");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pp() {
        this.setAttr("title", "食坊-我的");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ppr() {
        this.setAttr("title", "食坊-协议");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }
}
