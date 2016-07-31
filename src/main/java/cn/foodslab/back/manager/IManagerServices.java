package cn.foodslab.back.manager;

import cn.foodslab.back.common.IResultSet;

/**
 * Created by Pengwei Ding on 2016-07-30 10:11.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员数据服务层
 * 由流程控制层调用该层接口，返回结果集
 */
public interface IManagerServices {



    /**
     * 仅仅更新管理员的状态
     * @param username 判断要创建的用户名是否存在
     * @return
     */
    IResultSet isExistManagerUserName(String username);

    /**
     * 创建一个管理员
     * @param managerEntity 新的管理员信息，这个时候的对象并不包含ID信息，或许包含菜单映射
     * @return
     */
    IResultSet createManager(ManagerEntity managerEntity);

    /**
     * 更新管理员信息
     * @param managerEntity 要更新的管理员信息
     *                      注意1：更新Menu映射表的规则是先根据ID删除，后插入新的映射
     *                      注意2：要采用事务操作
     * @return
     */
    IResultSet updateManager(ManagerEntity managerEntity);

    /**
     * 读取所有管理员信息
     * @return
     */
    IResultSet retrieveManager();

}
