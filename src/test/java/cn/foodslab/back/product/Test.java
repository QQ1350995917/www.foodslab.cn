package cn.foodslab.back.product;

import com.alibaba.fastjson.JSON;

/**
 * Created by Pengwei Ding on 2016-08-05 22:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {
    public static void main(String[] args) {
        FormatEntity formatEntity = new FormatEntity();
        formatEntity.setLabel("450");
        formatEntity.setMeta("ml");
        formatEntity.setAmount(1);
        formatEntity.setAmountMeta("瓶");
        formatEntity.setPricing(180);
        formatEntity.setPricingMeta("￥");
        formatEntity.setPostage(10);
        formatEntity.setPostageMeta("￥");
        formatEntity.setTypeId("aba4d190-6874-426a-883f-a1e561a6f879");

        System.out.println(JSON.toJSONString(formatEntity));
    }
}
