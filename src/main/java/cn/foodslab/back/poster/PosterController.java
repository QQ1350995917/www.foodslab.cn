package cn.foodslab.back.poster;

import cn.foodslab.back.common.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-15 15:57.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class PosterController extends Controller implements IPostController {

    IPosterServices posterServices = new PosterServices();

    @Override
    public void index() {
        IResultSet resultSet = posterServices.retrieve();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        int status = 0;
        if (isParaExists("status")) {
            status = this.getParaToInt("status");
        }
        int clickable = 0;
        if (isParaExists("clickable")) {
            clickable = this.getParaToInt("clickable");
        }
        String href = this.getPara("href");
        String pid = this.getPara("pid");
        String posterId;
        if (pid == null) {
            posterId = UUID.randomUUID().toString();
            pid = posterId;
        } else {
            posterId = UUID.randomUUID().toString();
        }

        PosterEntity posterEntity = new PosterEntity(posterId, status, clickable, href, null, null, pid);
        IResultSet resultSet = posterServices.create(posterEntity);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {
        String posterId = this.getPara("posterId");
        int status = this.getParaToInt("status");
        int clickable = this.getParaToInt("clickable");
        String href = this.getPara("href");
        String start = this.getPara("start");
        String end = this.getPara("end");
        String pid = this.getPara("pid");
        PosterEntity posterEntity = new PosterEntity(posterId, status, clickable, href, start, end, pid);
        IResultSet resultSet = posterServices.update(posterEntity);
        renderJson(JSON.toJSONString(resultSet));
    }
}
