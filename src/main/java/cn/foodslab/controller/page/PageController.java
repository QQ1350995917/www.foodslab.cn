package cn.foodslab.controller.page;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.interceptor.ManagerInterceptor;
import cn.foodslab.interceptor.SessionInterceptor;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
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
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.css\">");
        this.setAttr("styleSheets", styleSheets);
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ps() {
        this.setAttr("title", "食坊-系列");

        LinkedList<Map<String, String>> metas = new LinkedList<>();
        String seriesId = this.getPara("seriesId");
        if (seriesId != null) {
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId", "seriesId");
            formatMap.put("metaValue", seriesId);
            metas.add(formatMap);
            this.setAttr("metas", metas);
        }
        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
        this.setAttr("styleSheets", styleSheets);
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/series.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pd() {
        this.setAttr("title", "食坊-详情");
        LinkedList<Map<String, String>> metas = new LinkedList<>();
        String typeId = this.getPara("typeId");
        if (typeId != null) {
            LinkedHashMap<String, String> typeMap = new LinkedHashMap<>();
            typeMap.put("metaId", "typeId");
            typeMap.put("metaValue", typeId);
            metas.add(typeMap);
        }
        String formatId = this.getPara("formatId");
        if (formatId != null) {
            LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
            formatMap.put("metaId", "formatId");
            formatMap.put("metaValue", formatId);
            metas.add(formatMap);
        }
        this.setAttr("metas", metas);

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/detail.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pb() {
        String params = this.getPara("p");
        Map<String, Object> objectMap = JSON.parseObject(params, Map.class);

        LinkedList<Map<String, String>> metas = new LinkedList<>();
        LinkedHashMap<String, String> mappingIdsMap = new LinkedHashMap<>();
        mappingIdsMap.put("metaId", "productIds");
        mappingIdsMap.put("metaValue", objectMap.get("productIds").toString());
        metas.add(mappingIdsMap);

        this.setAttr("metas", metas);

        this.setAttr("title", "食坊-支付");
        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_anonymous.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_named.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/billing_payment.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }


    @Override
    public void pq() {
        this.setAttr("title", "食坊-订单查询");
        String params = this.getPara("p");
        Map<String,Object> requestEntity = JSON.parseObject(params, Map.class);
        LinkedList<Map<String, String>> metas = new LinkedList<>();
        if (requestEntity != null && requestEntity.get("orderId") != null){
            String orderId = requestEntity.get("orderId").toString();
            if (orderId != null) {
                LinkedHashMap<String, String> formatMap = new LinkedHashMap<>();
                formatMap.put("metaId", "orderId");
                formatMap.put("metaValue", orderId);
                metas.add(formatMap);
            }
        }
        this.setAttr("metas", metas);

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mask.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/login.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/query.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Before(SessionInterceptor.class)
    @Override
    public void pm() {
        String params = this.getPara("p");
        VPageEntity vPageEntity = JSON.parseObject(params, VPageEntity.class);
        if (vPageEntity.getDir() == null) {
            vPageEntity.setDir("cart");
        }

        LinkedList<Map<String, String>> metas = new LinkedList<>();

        LinkedHashMap<String, String> dirMap = new LinkedHashMap<>();
        dirMap.put("metaId", "dir");
        dirMap.put("metaValue", vPageEntity.getDir());
        metas.add(dirMap);

        this.setAttr("metas", metas);
        this.setAttr("title", "食坊-我的");

        LinkedList<String> styleSheets = new LinkedList<>();
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_cart.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_order.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_account.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_receiver.css\">");
        styleSheets.add("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.css\">");
        this.setAttr("styleSheets", styleSheets);

        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/receiver.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_cart.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_order.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_account.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine_receiver.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/mine.js\"></script>");
        this.setAttr("javaScripts", javaScripts);

        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void pp() {
        this.setAttr("title", "食坊-协议");
        LinkedList<String> javaScripts = new LinkedList<>();
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/toast.js\"></script>");
        javaScripts.add("<script type=\"text/javascript\" src=\"" + this.getRequest().getContextPath() + "/webapp/asserts/index.js\"></script>");
        this.setAttr("javaScripts", javaScripts);
        this.render("/webapp/widgets/index.html");
    }

    @Override
    public void ml() {
        this.render("/webapp/widgets/login.html");
    }

    @Override
    @Before({SessionInterceptor.class, ManagerInterceptor.class})
    public void frame() {
        String params = getPara("p");
        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
        HttpSession session = SessionContext.getSession(paramsMap.get("cs").toString());
        // TODO 把JS文件进行拆分 根据管理员的权限进行动态分配JS文件
        this.render("/webapp/widgets/frame.html");
    }

    public void api() {
        this.render("/webapp/widgets/api.html");
    }
}
