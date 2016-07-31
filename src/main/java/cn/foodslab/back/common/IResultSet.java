package cn.foodslab.back.common;

/**
 * Created by Pengwei Ding on 2016-07-30 10:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: WEB-SERVICE-API
 */
public interface IResultSet {
    /**
     * 自定义结果码
     *
     * @return
     */
    int getCode();

    /**
     * 返回的数据
     *
     * @return
     */
    String getData();

    /**
     * 自定义结果提示
     *
     * @return
     */
    String getMessage();

    /**
     * 集成另外一个IResultSet对象
     * @param iResultSet 另外一个IResultSet对象
     * @param iResultSet
     * @return
     */
    IResultSet addIResultSet(IResultSet iResultSet);
}
