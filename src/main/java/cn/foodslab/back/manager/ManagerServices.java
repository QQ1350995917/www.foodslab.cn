package cn.foodslab.back.manager;

import cn.foodslab.back.common.IResultSet;
import cn.foodslab.back.common.ResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-07-30 11:01.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 */
public class ManagerServices implements IManagerServices {

    @Override
    public IResultSet isExistManagerUserName(String username) {
        List<Record> records = Db.find("SELECT * FROM manager WHERE username = '" + username + "'");
        if (records.size() == 1) {
            return new ResultSet("true");
        } else {
            return new ResultSet("false");
        }
    }

    @Override
    public IResultSet createManager(ManagerEntity managerEntity) {
        IResultSet existManagerUserName = isExistManagerUserName(managerEntity.getUsername());
        if ("true".equals(existManagerUserName.getData().toString())) {
            return new ResultSet(JSON.toJSONString(managerEntity), 0, "用户名已存在");
        } else if ("false".equals(existManagerUserName.getData().toString())) {
            ResultSet resultSet = new ResultSet();
            //开启事务 TODO 更新失败，并且回滚也失败了怎么办？
            boolean tx = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    //生成管理员ID
                    String managerId = UUID.randomUUID().toString();
                    Record manager = new Record()
                            .set("managerId", managerId)
                            .set("username", managerEntity.getUsername())
                            .set("password", managerEntity.getPassword())
                            .set("level", managerEntity.getLevel())
                            .set("status", managerEntity.getStatus())
                            .set("pId", managerEntity.getpId());
                    boolean managerResult = Db.save("manager", manager);
                    //对创建管理员结果做记录
                    if (!managerResult) {
                        resultSet.setMessage("创建管理员[" + managerEntity.getUsername() + "]失败;");
                    }

                    LinkedList<ManagerMenuEntity> manMenuEntities = managerEntity.getManagerMenuEntitiesMapping();
                    if (manMenuEntities != null && manMenuEntities.size() > 0) {
                        boolean managerMenuResult = true;
                        for (ManagerMenuEntity managerMenuEntity : manMenuEntities) {
                            Record managerMenu = new Record()
                                    .set("managerId", managerId)
                                    .set("menuId", managerMenuEntity.getMenuId()).set("menuLabel", managerMenuEntity.getMenuLabel());
                            managerMenuResult = Db.save("manager_menu", managerMenu);
                            //对创建管理员和菜单的映射管理做记录
                            if (!managerMenuResult) {
                                resultSet.setMessage(resultSet.getMessage() + "创建管理员-菜单映射[" + managerMenuEntity.getMenuLabel() + "]失败;");
                                break;
                            }
                        }
                        return managerResult && managerMenuResult;
                    } else {
                        //创建不含菜单映射的管理员
                        return managerResult;
                    }
                }
            });
            return resultSet;
        } else {
            //用户名重复检测结果在期望值之外的情况
            return new ResultSet(JSON.toJSONString(managerEntity), 0, "用户名检测结果是:" + existManagerUserName + "超出预定结果范围");
        }
    }

    @Override
    public IResultSet updateManager(ManagerEntity managerEntity) {
        ResultSet resultSet = new ResultSet();
        if (managerEntity.isUsernameU()) {
            resultSet.addIResultSet(this.updateManagerUsername(managerEntity));
        }
        if (managerEntity.isStatusU()) {
            resultSet.addIResultSet(this.updateManagerStatus(managerEntity));
        }
        if (managerEntity.isPasswordU()) {
            resultSet.addIResultSet(this.updateManagerPassword(managerEntity));
        }
        if (managerEntity.isManagerMenusMappingU()) {
            resultSet.addIResultSet(this.updateManagerMenusMapping(managerEntity));
        }
        return resultSet;
    }

    @Override
    public IResultSet retrieveManager() {
        List<Record> records = Db.find("SELECT * FROM manager ORDER BY createTime");
        List<Map> jsonMap = new LinkedList<>();
        for (Record record : records) {
            Map<String, Object> columns = record.getColumns();
            LinkedList<Map> menus = new LinkedList<>();
            List<Record> menuRecords = Db.find("SELECT * FROM manager_menu WHERE managerId='" + columns.get("managerId") + "'");
            for (Record menuRecord:menuRecords){
                Map<String, Object> menuRecordColumns = menuRecord.getColumns();
                menus.add(menuRecordColumns);
            }
            columns.put("menus",menus);
            jsonMap.add(columns);
        }
        IResultSet resultSet = new ResultSet(jsonMap);
        return resultSet;
    }

    /**
     * 仅仅更新管理员的用户名，即登录名
     *
     * @param managerEntity 要更新的管理员信息
     * @return
     */
    private IResultSet updateManagerUsername(ManagerEntity managerEntity) {
        ResultSet resultSet = new ResultSet();
        IResultSet existManagerUserName = isExistManagerUserName(managerEntity.getUsername());
        if ("true".equals(existManagerUserName)) {
            resultSet.setMessage("用户名[" + managerEntity.getUsername() + "]已存在，更新失败;");
            return resultSet;
        } else if ("false".equals(existManagerUserName)) {
            Record manager = Db.findById("manager", "managerId", managerEntity.getManagerId()).set("username", managerEntity.getStatus());
            boolean managerUpdate = Db.update("manager", manager);
            if (managerUpdate) {
                resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的用户名成功;");
            } else {
                resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的用户名失败;");
            }
            return resultSet;
        } else {
            resultSet.setMessage("用户名[" + managerEntity.getUsername() + "]的检测结果[" + existManagerUserName + "]超出预定结果范围");
            return resultSet;
        }
    }

    /**
     * 仅仅更新管理员的密码
     *
     * @param managerEntity 要更新的管理员信息
     * @return
     */
    private IResultSet updateManagerPassword(ManagerEntity managerEntity) {
        ResultSet resultSet = new ResultSet();
        Record manager = Db.findById("manager", "managerId", managerEntity.getManagerId()).set("password", managerEntity.getPassword());
        boolean managerUpdate = Db.update("manager", manager);
        if (managerUpdate) {
            resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的密码成功;");
        } else {
            resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的密码失败;");
        }
        return resultSet;
    }

    /**
     * 仅仅更新管理员的状态
     *
     * @param managerEntity 要更新的管理员信息
     * @return
     */
    private IResultSet updateManagerStatus(ManagerEntity managerEntity) {
        ResultSet resultSet = new ResultSet();
        Record manager = Db.findById("manager", "managerId", managerEntity.getManagerId()).set("status", managerEntity.getStatus());
        boolean managerUpdate = Db.update("manager", manager);
        if (managerUpdate) {
            resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的状态成功;");
        } else {
            resultSet.setMessage("更新[" + managerEntity.getUsername() + "]的状态失败;");
        }
        return resultSet;
    }

    /**
     * 仅仅更新管理员名下的菜单
     *
     * @param managerEntity 要更新的管理员信息
     * @return
     */
    private IResultSet updateManagerMenusMapping(ManagerEntity managerEntity) {
        ResultSet resultSet = new ResultSet();
        //删除菜单映射
        Db.update("DELETE FROM manager_menu WHERE managerId='" + managerEntity.getManagerId() + "'");
        LinkedList<ManagerMenuEntity> manMenuEntities = managerEntity.getManagerMenuEntitiesMapping();
        if (manMenuEntities != null && manMenuEntities.size() > 0) {
            boolean managerMenuResult;
            for (ManagerMenuEntity managerMenuEntity : manMenuEntities) {
                Record managerMenu = new Record()
                        .set("managerId", managerEntity.getManagerId())
                        .set("menuId", managerMenuEntity.getMenuId()).set("menuLabel", managerMenuEntity.getMenuLabel());
                managerMenuResult = Db.save("manager_menu","managerId,menuId", managerMenu);
                //对创建管理员和菜单的映射管理做记录
                if (managerMenuResult) {
                    resultSet.setMessage(resultSet.getMessage() + "创建管理员-菜单映射[" + managerMenuEntity.getMenuLabel() + "]成功;");
                } else {
                    resultSet.setMessage(resultSet.getMessage() + "创建管理员-菜单映射[" + managerMenuEntity.getMenuLabel() + "]失败;");
                }
            }
        }
        return resultSet;
    }
}
