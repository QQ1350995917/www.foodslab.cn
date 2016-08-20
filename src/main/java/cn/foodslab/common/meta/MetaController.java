package cn.foodslab.common.meta;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

/**
 * Created by Pengwei Ding on 2016-08-11 14:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 元数据
 */
public class MetaController extends Controller implements IMetaController{

    private IMetaServices metaServices = new MetaServices();

    public void index(){
        IResultSet resultSet = metaServices.retrieveUnit();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void unit() {
        IResultSet resultSet = metaServices.retrieveUnit();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void address() {
        String pid = this.getPara("pcode");
        IResultSet resultSet = metaServices.retrieveAddress(pid);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void link() {
        IResultSet resultSet = metaServices.retrieveLink();
        renderJson(JSON.toJSONString(resultSet));
    }
}
