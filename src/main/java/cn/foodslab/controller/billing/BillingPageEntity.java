package cn.foodslab.controller.billing;

import cn.foodslab.service.receiver.ReceiverEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-11 08:52.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 支付的View层数据对象
 */
public class BillingPageEntity {
    private LinkedList<ReceiverEntity> receivers;
    private LinkedList<BillingProductEntity> products;
    private int counter;
    private float cost;
    private float postage;

    public BillingPageEntity() {
        super();
    }

    public BillingPageEntity(LinkedList<ReceiverEntity> receivers, LinkedList<BillingProductEntity> products) {
        this.receivers = receivers;
        this.products = products;
    }

    public LinkedList<ReceiverEntity> getReceivers() {
        return receivers;
    }

    public void setReceivers(LinkedList<ReceiverEntity> receivers) {
        this.receivers = receivers;
    }

    public LinkedList<BillingProductEntity> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<BillingProductEntity> products) {
        this.products = products;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }
}
