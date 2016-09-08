package cn.foodslab.page;

import com.jfinal.core.Controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-08-19 16:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转均由该类实现的接口分发
 */
public class PageController extends Controller implements IPageController {

    @Override
    public void index() {
        this.setAttr("title", "食坊");
        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.css\">");
        this.setAttr("styleSheets", styleSheets);
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ps() {
        this.setAttr("title", "食坊-系列");
        LinkedList<Map<String,String>> metas = new LinkedList<>();
        String seriesId = this.getPara("seriesId");
        if (seriesId != null){
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId","seriesId");
            formatMap.put("metaValue", seriesId);
            metas.add(formatMap);
            this.setAttr("metas", metas);
        }
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/series.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pd() {
        this.setAttr("title", "食坊-详情");
        LinkedList<Map<String,String>> metas = new LinkedList<>();
        String typeId = this.getPara("typeId");
        if (typeId != null){
            LinkedHashMap<String, String> typeMap = new LinkedHashMap<>();
            typeMap.put("metaId","typeId");
            typeMap.put("metaValue",typeId);
            metas.add(typeMap);
        }
        String formatId = this.getPara("formatId");
        if (formatId != null){
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId","formatId");
            formatMap.put("metaValue", formatId);
            metas.add(formatMap);
        }
        this.setAttr("metas", metas);

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pb() {
        LinkedList<Map<String,String>> metas = new LinkedList<>();
        String formatIds = this.getPara("formatId");
        if (formatIds != null){
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId","formatIds");
            formatMap.put("metaValue", formatIds);
            metas.add(formatMap);
        }
        this.setAttr("metas", metas);
        this.setAttr("title", "食坊-支付");
        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/payment.js\"></script>");
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
    public void pq() {
        this.setAttr("title", "食坊-订单查询");

        String orderId = this.getPara("orderId");
        LinkedList<Map<String,String>> metas = new LinkedList<>();
        if (orderId != null){
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId","orderId");
            formatMap.put("metaValue", orderId);
            metas.add(formatMap);
        }
        this.setAttr("metas", metas);

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pm() {
        String dir = this.getPara("dir");
        String account = this.getPara("account");
        if (dir == null){
            dir = "cart";
        }

        LinkedList<Map<String,String>> metas = new LinkedList<>();

        LinkedHashMap<String, String> dirMap = new LinkedHashMap<>();
        dirMap.put("metaId","dir");
        dirMap.put("metaValue", dir);
        metas.add(dirMap);

        LinkedHashMap<String, String> accountMap = new LinkedHashMap<>();
        accountMap.put("metaId","account");
        accountMap.put("metaValue", account);
        metas.add(accountMap);

        this.setAttr("metas", metas);

        this.setAttr("title", "食坊-我的");

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/cart.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/order.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/account.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/cart.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/order.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/account.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.js\"></script>");
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

    @Override
    public void login() {
        this.render("/webapp/widgets/login.html");
    }

    @Override
    public void frame() {
        this.render("/webapp/widgets/frame.html");
    }
}
