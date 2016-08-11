package cn.foodslab.back.meta;

import cn.foodslab.back.common.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-08-11 14:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 元数据
 */
public class MetaController extends Controller {
    private MetaServices metaServices = new MetaServices();

    public void index(){
        IResultSet resultSet = metaServices.retrieveUnit();
        renderJson(JSON.toJSONString(resultSet));
    }

}
