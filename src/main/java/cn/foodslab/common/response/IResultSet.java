package cn.foodslab.common.response;

/**
 * Created by Pengwei Ding on 2016-07-30 10:14.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: WEB-SERVICE-API
 */
public interface IResultSet {
    /**
     * 枚举数据响应码
     * 响应吗由四位数字组成,前两位(基数是10,步长是10)标记大分类,后两位(基数是00,步长是1)标记小分类.
     */
    enum ResultCode {
        RC_SUCCESS(200),//执行成功
        RC_SUCCESS_EMPTY(204),//执行成功,符合请求条件的参数是空
        RC_PARAMS_BAD(400),//提交参数不符合要求
        RC_ACCESS_BAD(401),//权限限制的无法访问
        RC_ACCESS_TIMEOUT(408),//权限超时造成的无法访问
        RC_TO_MANY(429),//访问频率造成的拒绝服务
        RC_SEVER_ERROR(500),//服务器内部异常导致的失败



        EXE_FAIL(3000),
        EXE_SESSION_TIME_OUT(3011),
        EXE_SUCCESS(3050);

        private int code;
        ResultCode(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    class ResultMessage{
        public static final String RM_PARAMETERS_BAD = "PARAMETERS BAD";//请求参数错误
        public static final String RM_LOGIN_FAIL = "LOGIN NAME OR PASSWORD FAIL";//用户名或密码错误
        public static final String RM_LOGIN_SUCCESS = "LOGIN SUCCESS";//登录成功
        public static final String RM_ACCESS_BAD = "ACCESS BAD";//权限问题
        public static final String RM_ACCESS_TIMEOUT = "LOGIN TIMEOUT";//登录超时
        public static final String RM_SERVER_ERROR = "SERVER ERROR";//服务器执行错误
        public static final String RM_SERVER_OK = "SERVER OK";//服务器执行完成
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
