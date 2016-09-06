package cn.foodslab.cart;

/**
 * Created by Pengwei Ding on 2016-09-05 11:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CartEntity {

    private String mappingId;
    private String formatId;
    private int amount;
    private int status;
    private String accountId;
    private String createTime;
    private String updateTime;

    public CartEntity() {
        super();
    }

    public CartEntity(String mappingId, String formatId, int amount, int status, String accountId, String createTime, String updateTime) {
        this.mappingId = mappingId;
        this.formatId = formatId;
        this.amount = amount;
        this.status = status;
        this.accountId = accountId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CartEntity(String formatId, int amount, String accountId) {
        this.formatId = formatId;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
