package cn.foodslab.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by Pengwei Ding on 2016-09-21 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Session implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
    }
}
