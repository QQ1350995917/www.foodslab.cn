package cn.foodslab.controller.billing;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.service.order.IOrder2ProductServices;
import cn.foodslab.service.order.Order2ProductEntity;
import cn.foodslab.service.order.Order2ProductServices;
import cn.foodslab.service.product.FormatEntity;
import cn.foodslab.service.product.IProductServices;
import cn.foodslab.service.product.ProductServices;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
import cn.foodslab.model.billing.BillingPageEntity;
import cn.foodslab.model.billing.BillingProductEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-09 17:13.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class BillingController extends Controller implements IBillingController {
    private IAccountServices iAccountServices = new AccountServices();
    private IReceiverService iReceiverService = new ReceiverServices();
    private IOrder2ProductServices iOrder2ProductServices = new Order2ProductServices();
    private IProductServices iProductServices = new ProductServices();

    @Override
    public void index() {
        String accountId = this.getPara("accountId");
        String productIds = this.getPara("productIds");
        if (accountId != null) {
            LinkedList<ReceiverEntity> receiverEntities = iReceiverService.retrieve(iAccountServices.retrieveUserByAccountId(accountId));
            LinkedList<BillingProductEntity> billingProductEntities = new LinkedList<>();
            if (productIds != null) {
                String[] split = productIds.split(",");
                for(String productId : split){
                    LinkedList<Order2ProductEntity> order2ProductEntities = iOrder2ProductServices.retrieve(productId);
                    for (Order2ProductEntity order2ProductEntity : order2ProductEntities) {
                        BillingProductEntity billingProductEntity = new BillingProductEntity();

                        FormatEntity formatEntity = iProductServices.retrieveTreeByFormatId(order2ProductEntity.getFormatId());
//                    billingProductEntity.setIcon(formatEntity.getParent().getImageEntities().get(0).getFilePath());
                        billingProductEntity.setSeriesName(formatEntity.getParent().getParent().getLabel());
                        billingProductEntity.setTypeName(formatEntity.getParent().getLabel());
                        billingProductEntity.setFormatName(formatEntity.getLabel());
                        billingProductEntity.setFormatMeta(formatEntity.getMeta());
                        billingProductEntity.setPricing(formatEntity.getPricing());
                        billingProductEntity.setPriceMeta(formatEntity.getPriceMeta());
                        billingProductEntity.setAmount(order2ProductEntity.getAmount());
                        billingProductEntity.setFormatId(formatEntity.getFormatId());

                        billingProductEntities.add(billingProductEntity);
                    }
                }
            }
            BillingPageEntity billingPageEntity = new BillingPageEntity(receiverEntities, billingProductEntities);
            IResultSet iResultSet = new ResultSet(billingPageEntity);
            iResultSet.setCode(200);
            renderJson(JSON.toJSONString(iResultSet));
        } else {
            if (productIds == null) {
                IResultSet iResultSet = new ResultSet();
                iResultSet.setCode(500);
                iResultSet.setMessage("参数异常");
                renderJson(JSON.toJSONString(iResultSet));
            } else {
                LinkedList<BillingProductEntity> billingProductEntities = new LinkedList<>();
                BillingProductEntity billingProductEntity = new BillingProductEntity();
                FormatEntity formatEntity = iProductServices.retrieveTreeByFormatId(productIds);
//                    billingProductEntity.setIcon(formatEntity.getParent().getImageEntities().get(0).getFilePath());
                billingProductEntity.setSeriesName(formatEntity.getParent().getParent().getLabel());
                billingProductEntity.setTypeName(formatEntity.getParent().getLabel());
                billingProductEntity.setFormatName(formatEntity.getLabel());
                billingProductEntity.setFormatMeta(formatEntity.getMeta());
                billingProductEntity.setPricing(formatEntity.getPricing());
                billingProductEntity.setPriceMeta(formatEntity.getPriceMeta());
                billingProductEntity.setAmount(1);
                billingProductEntity.setFormatId(formatEntity.getFormatId());
                billingProductEntities.add(billingProductEntity);

                BillingPageEntity billingPageEntity = new BillingPageEntity(null, billingProductEntities);
                IResultSet iResultSet = new ResultSet(billingPageEntity);
                iResultSet.setCode(200);
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

}
