package cn.foodslab.common.response;

/**
 * Created by Pengwei Ding on 2016-07-30 10:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: WEB-SERVICE-API
 */
public interface IResultSet {
    enum ResultCode {

        EXE_FAIL(3000),
        EXE_SUCCESS(3050);

        private int code;
        ResultCode(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 自定义结果码
     *
     * @return
     */
    int getCode();

    void setCode(int code);

    Object getData();

    void setData(Object data);

    /**
     * 返回的数据
     *
     * @return
     */
    String getJsonDataString();

    /**
     * 自定义结果提示
     *
     * @return
     */
    String getMessage();

    void setMessage(String message);

    /**
     * 集成另外一个IResultSet对象
     *
     * @param iResultSet 另外一个IResultSet对象
     * @param iResultSet
     * @return
     */
    IResultSet addIResultSet(IResultSet iResultSet);
}
