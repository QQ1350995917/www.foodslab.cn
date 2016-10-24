package cn.foodslab.controller.page;

/**
 * Created by Pengwei Ding on 2016-08-19 16:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 前端页面接口，所有有关页面的跳转分发接口
 */
public interface IPageController {
    /**
     * 默认情况下的页面跳转
     * 指定跳转页面异常情况下的跳转
     */
    void index();

    /**
     * short of Page Series
     * 跳转到系列页面
     */
    void ps();

    /**
     * short of Page Detail
     * 跳转到详情页面
     */
    void pd();

    /**
     * short of Page Billing
     * 跳转到支付页面
     */
    void pb();

    /**
     * short of Page Look
     * 跳转到订单查询页面
     */
    void pq();

    /**
     * short of Page Mine
     * 跳转到用户信息页面
     */
    void pm();

    /**
     * short of Page Protocol
     * 跳转到网站协议页面
     */
    void pp();

    /**
     * 管理员登录界面
     */
    void ml();

    /**
     * 管理员界面
     * 访问该页面时候要进行Session类型的检测，管理员类型的Session才允许访问该页面
     */
    void frame();


}
