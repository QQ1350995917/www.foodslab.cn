package cn.foodslab.back.manager;

/**
 * Created by Pengwei Ding on 2016-07-28 16:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 管理员操作接口
 */
public interface IManagerDao {

    /**
     * 初始化系统管理员
     * @param managerEntity 系统管理员
     * @return 执行结果
     * @throws Exception
     */
    boolean initManager(ManagerEntity managerEntity) throws Exception;

    /**
     * 创建一个管理员
     * @param username
     * @param password
     * @param pid
     * @param menuIds
     * @return
     * @throws Exception
     */
    ManagerEntity createManager(String username,String password,String pid,String... menuIds)throws Exception;

    /**
     * 仅仅修改了用户名称
     * @param managerEntity
     * @return
     * @throws Exception
     */
    ManagerEntity updateManagerUsername(ManagerEntity managerEntity)throws Exception;

    /**
     * 仅仅修改了用户状态
     * @param managerEntity
     * @return
     * @throws Exception
     */
    ManagerEntity updateManagerStatus(ManagerEntity managerEntity)throws Exception;

    /**
     * 仅仅修改了用户密码
     * @param managerEntity
     * @return
     * @throws Exception
     */
    ManagerEntity updateManagerPassword(ManagerEntity managerEntity)throws Exception;

    /**
     * 仅仅修改了用户权限
     * @param managerEntity
     * @return
     * @throws Exception
     */
    ManagerEntity updateManagerMenuIds(ManagerEntity managerEntity)throws Exception;

    /**
     * 仅仅用户名，状态，密码，权限四项属性
     * @param managerEntity
     * @return
     * @throws Exception
     */
    ManagerEntity updateManager(ManagerEntity managerEntity)throws Exception;



}
