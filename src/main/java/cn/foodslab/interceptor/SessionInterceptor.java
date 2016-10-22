package cn.foodslab.interceptor;

import cn.foodslab.common.cache.SessionContext;
import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.Map;

/**
 * Created by Pengwei Ding on 2016-09-21 18:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 会话拦截器
 */
public class SessionInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        String params = inv.getController().getPara("p");
        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
        if (paramsMap == null || !paramsMap.containsKey("cs")) {
            IResultSet resultSet = new ResultSet(IResultSet.ResultCode.EXE_FAIL.getCode());
            resultSet.setData(paramsMap);
            resultSet.setMessage("参数错误");
            inv.getController().renderJson(resultSet);
        } else {
            Object cs = paramsMap.get("cs");
            if (cs == null || SessionContext.getSession(cs.toString()) == null) {
                IResultSet resultSet = new ResultSet(IResultSet.ResultCode.EXE_SESSION_TIME_OUT.getCode());
                resultSet.setData(paramsMap);
                resultSet.setMessage("登录超时");
                inv.getController().renderJson(resultSet);
            } else {
                inv.invoke();
            }
        }
    }
}
