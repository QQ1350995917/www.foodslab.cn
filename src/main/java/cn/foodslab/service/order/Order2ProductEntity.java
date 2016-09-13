package cn.foodslab.service.order;

/**
 * Created by Pengwei Ding on 2016-08-31 17:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Order2ProductEntity {

    private String mappingId;
    private String orderId;
    private String formatId;
    private int amount;
    private String createTime;
    private String updateTime;

    public Order2ProductEntity() {
        super();
    }

    public Order2ProductEntity(String mappingId, String orderId, String formatId,int amount, String createTime, String updateTime) {
        this.mappingId = mappingId;
        this.orderId = orderId;
        this.formatId = formatId;
        this.amount = amount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Order2ProductEntity(String mappingId, String orderId, String formatId) {
        this.mappingId = mappingId;
        this.orderId = orderId;
        this.formatId = formatId;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
