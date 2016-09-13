package cn.foodslab.service.manager;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-30 10:00.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员业务逻辑流程控制器
 */
public class ManagerController extends Controller implements IManagerController{

    private IManagerServices iManagerServices = new ManagerServices();

    @Override
    public void index() {
        IResultSet resultSet = iManagerServices.retrieveManager();
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void check() {
        String username = this.getPara("username");
        IResultSet resultSet = iManagerServices.isExistManagerUserName(username);
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String pid = this.getPara("pid");
        String username = this.getPara("username");
        String password = this.getPara("password");
        String menus = this.getPara("menus").replaceFirst(",","");
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setUsername(username);
        managerEntity.setPassword(password);
        managerEntity.setLevel(1);
        managerEntity.setQueue(0);
        managerEntity.setStatus(1);
        managerEntity.setpId(pid);
        LinkedList<ManagerMenuEntity> menuEntities = new LinkedList<>();
        for (String menu:menus.split(",")){
            menuEntities.add(new ManagerMenuEntity(null,menu.split(":")[0],menu.split(":")[1]));
        }
        managerEntity.setManagerMenuEntitiesMapping(menuEntities);
        IResultSet resultSet = iManagerServices.createManager(managerEntity);

        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void update() {
        String managerId = this.getPara("managerId");
        String menus = this.getPara("menus").replaceFirst(",", "");
        LinkedList<ManagerMenuEntity> menuEntities = new LinkedList<>();
        for (String menu:menus.split(",")){
            menuEntities.add(new ManagerMenuEntity(managerId,menu.split(":")[0],menu.split(":")[1]));
        }
        ManagerEntity managerEntity = new ManagerEntity();
        managerEntity.setManagerId(managerId);
        managerEntity.setIsManagerMenusMappingU(true);
        managerEntity.setManagerMenuEntitiesMapping(menuEntities);
        IResultSet resultSet = iManagerServices.updateManager(managerEntity);
        renderJson(JSON.toJSONString(resultSet));
    }
}
