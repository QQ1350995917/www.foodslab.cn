package cn.foodslab.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by Pengwei Ding on 2016-10-17 17:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员权限拦截
 */
public class ManagerInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
    }
}
