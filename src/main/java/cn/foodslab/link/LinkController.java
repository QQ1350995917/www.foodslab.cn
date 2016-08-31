package cn.foodslab.link;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-13 18:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class LinkController extends Controller implements ILinkController{

    ILinkServices iLinkServices = new LinkServices();

    @Override
    public void index() {
        IResultSet retrieve = iLinkServices.retrieve();
        renderJson(JSON.toJSONString(retrieve));
    }

    @Override
    public void create() {
        String linkId = UUID.randomUUID().toString();
        String label = this.getPara("label");
        String href = this.getPara("href");
        String pid = this.getPara("pid") == null ? linkId:this.getPara("pid");
        LinkEntity linkEntity = new LinkEntity(linkId,label,href,pid,1);
        IResultSet retrieve = iLinkServices.create(linkEntity);
        renderJson(JSON.toJSONString(retrieve));
    }

    @Override
    public void update() {
        String linkId = this.getPara("linkId");
        String label = this.getPara("label");
        String href = this.getPara("href");
        int status = this.getParaToInt("status");
        LinkEntity linkEntity = new LinkEntity(linkId,label,href,null,status);
        IResultSet retrieve = iLinkServices.update(linkEntity);
        renderJson(JSON.toJSONString(retrieve));

    }
}
