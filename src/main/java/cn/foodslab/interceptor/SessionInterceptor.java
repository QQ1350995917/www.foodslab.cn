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
        IResultSet iResultSet = new ResultSet();
        if (params == null || params.trim().equals("")){
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(params);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        Map<String, Object> paramsMap = JSON.parseObject(params, Map.class);
        if (paramsMap == null || !paramsMap.containsKey("cs") || paramsMap.get("cs") == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_PARAMS_BAD.getCode());
            iResultSet.setData(params);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_PARAMETERS_BAD);
            inv.getController().renderJson(iResultSet);
            return;
        }

        String cs = paramsMap.get("cs").toString();
        if (SessionContext.getSession(cs) == null) {
            iResultSet.setCode(IResultSet.ResultCode.RC_ACCESS_TIMEOUT.getCode());
            iResultSet.setData(paramsMap);
            iResultSet.setMessage(IResultSet.ResultMessage.RM_ACCESS_TIMEOUT);
            inv.getController().renderJson(JSON.toJSONString(iResultSet));
            return;
        }

        inv.invoke();
    }
}
