package cn.foodslab.back.common;

/**
 * Created by Pengwei Ding on 2016-07-30 13:20.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ResultSet implements IResultSet {
    private String data;
    private int code;
    private String message = "this is tip message!";

    public ResultSet() {
    }

    public ResultSet(String data) {
        this.data = data;
    }

    public ResultSet(String data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public IResultSet addIResultSet(IResultSet iResultSet) {
        this.setMessage(this.getMessage() + iResultSet.getMessage());
        return this;
    }
}
