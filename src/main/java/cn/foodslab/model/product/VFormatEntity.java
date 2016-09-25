package cn.foodslab.model.product;

import cn.foodslab.service.product.FormatEntity;

/**
 * Created by Pengwei Ding on 2016-09-23 11:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class VFormatEntity {
    private String sessionId;
    private String typeId;// 规格的类型ID
    private String formatId;
    private String label;// 规格的量 比如 1，100，500，2，3，4
    private String meta;// 规格的单位 比如 kg, mk，L，盒，包，瓶
    private int amount;// 规格下的数量 通常是在，盒，包下的包含个数，比如每盒有4瓶
    private String amountMeta;// 规格下的数量的单位
    private float price;// 定价
    private String priceMeta;// 定价单位 通常是￥
    private float postage;// 邮费
    private String postageMeta;// 邮费单位 通常是￥
    private float pricing;// 现价
    private float pricingDiscount;// 现价对比定价的折扣
    private long pricingStart;// 折扣活动开始时间
    private long pricingEnd;// 折扣活动结束时间
    private int pricingStatus;// 折扣活动所处于的状态，是否要显示
    private int expressCount;// 包邮需要的数量
    private String expressName;// 邮递公司名称
    private long expressStart;// 包邮活动开始时间
    private long expressEnd;// 包邮活动结束时间
    private int expressStatus;// 包邮活动所处于的状态，是否要显示
    private int giftCount;// 满赠需要的数量
    private String giftLabel;// 满赠产品的名称
    private String giftId;// 满赠产品规格的ID
    private long giftStart;// 满赠活动开始时间
    private long giftEnd;// 满赠活动结束时间
    private int giftStatus;// 满赠活动所处于的状态
    private int weight;// 该规格的顺序
    private int queue;
    private int status = -2;
    private VTypeEntity parent;

    public VFormatEntity() {
        super();
    }

    public VFormatEntity(FormatEntity formatEntity) {
        this.typeId = formatEntity.getTypeId();
        this.formatId = formatEntity.getFormatId();
        this.label = formatEntity.getLabel();
        this.meta = formatEntity.getMeta();
        this.amount = formatEntity.getAmount();
        this.amountMeta = formatEntity.getAmountMeta();
        this.price = formatEntity.getPrice();
        this.priceMeta = formatEntity.getPriceMeta();
        this.postage = formatEntity.getPostage();
        this.postageMeta = formatEntity.getPostageMeta();
        this.pricing = formatEntity.getPricing();
        this.pricingDiscount = formatEntity.getPricingDiscount();
        this.pricingStart = formatEntity.getPricingStart();
        this.pricingEnd = formatEntity.getPricingEnd();
        this.pricingStatus = formatEntity.getPricingStatus();
        this.expressCount = formatEntity.getExpressCount();
        this.expressName = formatEntity.getExpressName();
        this.expressStart = formatEntity.getExpressStart();
        this.expressEnd = formatEntity.getExpressEnd();
        this.expressStatus = formatEntity.getExpressStatus();
        this.giftCount = formatEntity.getGiftCount();
        this.giftLabel = formatEntity.getGiftLabel();
        this.giftId = formatEntity.getGiftId();
        this.giftStart = formatEntity.getGiftStart();
        this.giftEnd = formatEntity.getGiftEnd();
        this.giftStatus = formatEntity.getGiftStatus();
        this.weight = formatEntity.getWeight();
        this.queue = formatEntity.getQueue();
        this.status = formatEntity.getStatus();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getFormatId() {
        return formatId;
    }

    public void setFormatId(String formatId) {
        this.formatId = formatId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountMeta() {
        return amountMeta;
    }

    public void setAmountMeta(String amountMeta) {
        this.amountMeta = amountMeta;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceMeta() {
        return priceMeta;
    }

    public void setPriceMeta(String priceMeta) {
        this.priceMeta = priceMeta;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public String getPostageMeta() {
        return postageMeta;
    }

    public void setPostageMeta(String postageMeta) {
        this.postageMeta = postageMeta;
    }

    public float getPricing() {
        return pricing;
    }

    public void setPricing(float pricing) {
        this.pricing = pricing;
    }

    public float getPricingDiscount() {
        return pricingDiscount;
    }

    public void setPricingDiscount(float pricingDiscount) {
        this.pricingDiscount = pricingDiscount;
    }

    public long getPricingStart() {
        return pricingStart;
    }

    public void setPricingStart(long pricingStart) {
        this.pricingStart = pricingStart;
    }

    public long getPricingEnd() {
        return pricingEnd;
    }

    public void setPricingEnd(long pricingEnd) {
        this.pricingEnd = pricingEnd;
    }

    public int getPricingStatus() {
        return pricingStatus;
    }

    public void setPricingStatus(int pricingStatus) {
        this.pricingStatus = pricingStatus;
    }

    public int getExpressCount() {
        return expressCount;
    }

    public void setExpressCount(int expressCount) {
        this.expressCount = expressCount;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public long getExpressStart() {
        return expressStart;
    }

    public void setExpressStart(long expressStart) {
        this.expressStart = expressStart;
    }

    public long getExpressEnd() {
        return expressEnd;
    }

    public void setExpressEnd(long expressEnd) {
        this.expressEnd = expressEnd;
    }

    public int getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(int expressStatus) {
        this.expressStatus = expressStatus;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public String getGiftLabel() {
        return giftLabel;
    }

    public void setGiftLabel(String giftLabel) {
        this.giftLabel = giftLabel;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public long getGiftStart() {
        return giftStart;
    }

    public void setGiftStart(long giftStart) {
        this.giftStart = giftStart;
    }

    public long getGiftEnd() {
        return giftEnd;
    }

    public void setGiftEnd(long giftEnd) {
        this.giftEnd = giftEnd;
    }

    public int getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(int giftStatus) {
        this.giftStatus = giftStatus;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public VTypeEntity getParent() {
        return parent;
    }

    public void setParent(VTypeEntity parent) {
        this.parent = parent;
    }

    public FormatEntity getFormatEntity(){
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setTypeId(this.getTypeId());
        formatEntity.setFormatId(this.getFormatId());
        formatEntity.setLabel(this.getLabel());
        formatEntity.setMeta(this.getMeta());
        formatEntity.setAmount(this.getAmount());
        formatEntity.setAmountMeta(this.getAmountMeta());
        formatEntity.setPrice(this.getPrice());
        formatEntity.setPriceMeta(this.getPriceMeta());
        formatEntity.setPostage(this.getPostage());
        formatEntity.setPostageMeta(this.getPostageMeta());
        formatEntity.setPricing(this.getPricing());
        formatEntity.setPricingDiscount(this.getPricingDiscount());
        formatEntity.setPricingStart(this.getPricingStart());
        formatEntity.setPricingEnd(this.getPricingEnd());
        formatEntity.setPricingStatus(this.getPricingStatus());
        formatEntity.setExpressCount(this.getExpressCount());
        formatEntity.setExpressName(this.getExpressName());
        formatEntity.setExpressStart(this.getExpressStart());
        formatEntity.setExpressEnd(this.getExpressEnd());
        formatEntity.setExpressStatus(this.getExpressStatus());
        formatEntity.setGiftCount(this.getGiftCount());
        formatEntity.setGiftLabel(this.getGiftLabel());
        formatEntity.setGiftId(this.getGiftId());
        formatEntity.setGiftStart(this.getGiftStart());
        formatEntity.setGiftEnd(this.getGiftEnd());
        formatEntity.setGiftStatus(this.getGiftStatus());
        formatEntity.setWeight(this.getWeight());
        formatEntity.setQueue(this.getQueue());
        formatEntity.setStatus(this.getStatus());
        return formatEntity;
    }
}
