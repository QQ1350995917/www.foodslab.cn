package cn.foodslab.order;

/**
 * Created by Pengwei Ding on 2016-08-31 17:26.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class FormatMappingEntity {

    private String mappingId;
    private String orderId;
    private String formatId;
    private String createTime;
    private String updateTime;

    public FormatMappingEntity() {
        super();
    }

    public FormatMappingEntity(String mappingId, String orderId, String formatId, String createTime, String updateTime) {
        this.mappingId = mappingId;
        this.orderId = orderId;
        this.formatId = formatId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public FormatMappingEntity(String mappingId, String orderId, String formatId) {
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
