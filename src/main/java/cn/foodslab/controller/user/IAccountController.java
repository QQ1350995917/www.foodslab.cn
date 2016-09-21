package cn.foodslab.controller.user;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IAccountController {

    void index();

    /**
     * 用户登录接口
     */
    void retrieve();

    /**
     * 用户创建账户接口
     */
    void create();

    /**
     * 更新账户
     */
    void update();

    /**
     * 禁用账户
     * 管理员接口
     */
    void block();

    /**
     * 绑定或解绑账户
     */
    void bind();

    /**
     * 检测账号的标记是否已经注册
     * 账号标记：网站用户指的是电话号码，微信用户指的是微信的ID，QQ用户指的是QQ的ID
     */
    void exist();

    /**
     * 忘记密码接口
     */
    void password();

    /**
     * 用户修改头像
     */
    void portrait();

}
