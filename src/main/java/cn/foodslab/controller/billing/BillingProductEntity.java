package cn.foodslab.controller.billing;

/**
 * Created by Pengwei Ding on 2016-09-13 13:10.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BillingProductEntity {
    private String icon;
    private String seriesName;
    private String typeName;
    private String formatName;
    private String formatMeta;
    private float pricing;
    private String priceMeta;
    private int amount;
    private String formatId;

    public BillingProductEntity() {
        super();
    }

    public BillingProductEntity(String icon, String seriesName, String typeName, String formatName, String formatMeta, float pricing, String priceMeta, int amount) {
        this.icon = icon;
        this.seriesName = seriesName;
        this.typeName = typeName;
        this.formatName = formatName;
        this.formatMeta = formatMeta;
        this.pricing = pricing;
        this.priceMeta = priceMeta;
        this.amount = amount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getFormatMeta() {
        return formatMeta;
    }

    public void setFormatMeta(String formatMeta) {
        this.formatMeta = formatMeta;
    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public String getPriceMeta() {
        return priceMeta;
    }

    public void setPriceMeta(String priceMeta) {
        this.priceMeta = priceMeta;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }
}
