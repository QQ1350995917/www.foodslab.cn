package cn.foodslab.controller.billing;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.model.billing.BillingPageEntity;
import cn.foodslab.model.billing.BillingProductEntity;
import cn.foodslab.service.cart.CartEntity;
import cn.foodslab.service.cart.CartServices;
import cn.foodslab.service.cart.ICartServices;
import cn.foodslab.service.product.IProductServices;
import cn.foodslab.service.product.ProductServices;
import cn.foodslab.service.receiver.IReceiverService;
import cn.foodslab.service.receiver.ReceiverEntity;
import cn.foodslab.service.receiver.ReceiverServices;
import cn.foodslab.service.user.AccountServices;
import cn.foodslab.service.user.IAccountServices;
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
    private ICartServices iCartServices = new CartServices();
    private IProductServices iProductServices = new ProductServices();

    @Override
    public void retrieve() {

    }

    @Override
    public void index() {
        String accountId = this.getPara("accountId");
        String productIds = this.getPara("productIds");
        if (accountId != null) {
            LinkedList<ReceiverEntity> receiverEntities = iReceiverService.retrieveByUserId(iAccountServices.retrieveUserByAccountId(accountId).getUserId());
            LinkedList<BillingProductEntity> billingProductEntities = new LinkedList<>();
            if (productIds != null) {
                String[] split = productIds.split(",");
                for(String productId : split){
                    BillingProductEntity billingProductEntity = new BillingProductEntity();
                    CartEntity cartEntity = iCartServices.retrieveById(productId);

//                    FormatEntity formatEntity = iProductServices.retrieveTreeByFormatId(cartEntity.getFormatId());
////                    billingProductEntity.setIcon(formatEntity.getParent().getImageEntities().get(0).getFilePath());
//                    billingProductEntity.setSeriesName(formatEntity.getParent().getParent().getLabel());
//                    billingProductEntity.setTypeName(formatEntity.getParent().getLabel());
//                    billingProductEntity.setFormatName(formatEntity.getLabel());
//                    billingProductEntity.setFormatMeta(formatEntity.getMeta());
//                    billingProductEntity.setPricing(formatEntity.getPricing());
//                    billingProductEntity.setPriceMeta(formatEntity.getPriceMeta());
//                    billingProductEntity.setAmount(cartEntity.getAmount());
//                    billingProductEntity.setFormatId(formatEntity.getFormatId());
//
//                    billingProductEntities.add(billingProductEntity);
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
//                FormatEntity formatEntity = iProductServices.retrieveTreeByFormatId(productIds);
////                    billingProductEntity.setIcon(formatEntity.getParent().getImageEntities().get(0).getFilePath());
//                billingProductEntity.setSeriesName(formatEntity.getParent().getParent().getLabel());
//                billingProductEntity.setTypeName(formatEntity.getParent().getLabel());
//                billingProductEntity.setFormatName(formatEntity.getLabel());
//                billingProductEntity.setFormatMeta(formatEntity.getMeta());
//                billingProductEntity.setPricing(formatEntity.getPricing());
//                billingProductEntity.setPriceMeta(formatEntity.getPriceMeta());
//                billingProductEntity.setAmount(1);
//                billingProductEntity.setFormatId(formatEntity.getFormatId());
//                billingProductEntities.add(billingProductEntity);

                BillingPageEntity billingPageEntity = new BillingPageEntity(null, billingProductEntities);
                IResultSet iResultSet = new ResultSet(billingPageEntity);
                iResultSet.setCode(200);
                renderJson(JSON.toJSONString(iResultSet));
            }
        }
    }

}
