package cn.foodslab.model.query;

/**
 * Created by Pengwei Ding on 2016-09-14 13:58.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class QueryPageEntity {
    private String orderId;
    private String orderTime;
    private String address;
    private String name;
    private String phone;
    private String expressName;
    private String expressNumber;
    private String expressStatus;

    public QueryPageEntity() {
        super();
    }

    public QueryPageEntity(String orderId, String orderTime, String address, String name, String phone, String expressName, String expressNumber, String expressStatus) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.expressName = expressName;
        this.expressNumber = expressNumber;
        this.expressStatus = expressStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }
}
