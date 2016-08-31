package cn.foodslab.login;

import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-07-30 11:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 登录流程控制层
 */
public class LoginController extends Controller implements ILoginController{

    private ILoginServices iLoginServices = new LoginServices();

    @Override
    public void login() {
        String username = "";
        String password = "";
        renderJson(iLoginServices.retrieveManager(username,password));
    }
}
