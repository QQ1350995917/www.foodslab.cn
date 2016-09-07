package cn.foodslab.page;

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
     * short of Page Cart
     * 跳转到购物车页面
     */
    void pc();

    /**
     * short of Page Order
     * 跳转到订单页面
     */
    void po();

    /**
     * short of Page Exit
     * 退出跳转到首页
     */
    void pe();

    /**
     * short of Page Reservation
     * 跳转到预约页面
     */
    void pr();

    /**
     * short of Page Look
     * 跳转到订单查询页面
     */
    void pq();

    /**
     * short of Page Person
     * 跳转到用户信息页面
     */
    void pp();

    /**
     * short of Page Protocol
     * 跳转到网站协议页面
     */
    void ppr();

    /**
     * 管理员登录
     */
    void login();

    /**
     * 管理员界面
     */
    void frame();


}
