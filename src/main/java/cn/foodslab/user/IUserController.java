package cn.foodslab.user;

/**
 * Created by Pengwei Ding on 2016-08-16 08:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IUserController {

    void index();

    /**
     * 读取账户
     * 管理员接口
     */
    void retrieve();

    /**
     * 创建新的账户
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
     * 绑定账户
     */
    void bind();

}
