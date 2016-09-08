package cn.foodslab.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.receiver.IReceiverService;
import cn.foodslab.receiver.ReceiverEntity;
import cn.foodslab.receiver.ReceiverServices;
import cn.foodslab.user.AccountServices;
import cn.foodslab.user.IAccountServices;
import cn.foodslab.user.UserEntity;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderController extends Controller implements IOrderController {
    private IAccountServices iAccountServices = new AccountServices();
    private IOrderServices iOrderServices = new OrderServices();
    private IReceiverService iReceiverService = new ReceiverServices();
    private IOrderProductMappingServices iFormatMappingServices = new OrderProductMappingServices();

    @Override
    public void retrieve() {
        String accountId = getPara("accountId");
        UserEntity userEntity = iAccountServices.retrieveUserByAccountId(accountId);
        IResultSet resultSet = new ResultSet();
        if (userEntity != null) {
            LinkedList<OrderEntity> orderEntities = iOrderServices.retrieve(userEntity.getUserId());
            resultSet.setCode(200);
            resultSet.setData(orderEntities);
        } else {
            resultSet.setCode(500);
        }
        renderJson(JSON.toJSONString(resultSet));
    }

    @Override
    public void create() {
        String accountId = getPara("accountId");
        String senderName = getPara("senderName");
        String senderPhone = getPara("senderPhone");
        long cost = getParaToLong("cost");
        long postage = getParaToLong("postage");

        String formatId = getPara("formatId");

        String receiverId = UUID.randomUUID().toString();
        String province = getPara("province");
        String city = getPara("city");
        String county = getPara("county");
        String town = getPara("town");
        String village = getPara("village");
        String append = getPara("append");
        String name = getPara("name");
        String phone0 = getPara("phone0");
        String phone1 = getPara("phone1");
        ReceiverEntity receiverEntity = new ReceiverEntity(receiverId, province, city, county, town, village, append, name, phone0, phone1, 1, accountId);
        ReceiverEntity resultReceiver = iReceiverService.create(receiverEntity);
        IResultSet resultSet = new ResultSet();
        if (resultReceiver != null) {
            String orderId = UUID.randomUUID().toString();
            OrderEntity orderEntity = new OrderEntity(orderId, accountId, senderName, senderPhone, receiverEntity.getReceiverId(), cost, postage, 1);
            OrderEntity resultOrder = iOrderServices.create(orderEntity);
            if (resultOrder != null) {
                LinkedList<OrderProductMappingEntity> formatMappingEntities = new LinkedList<>();
                formatMappingEntities.add(new OrderProductMappingEntity(UUID.randomUUID().toString(), orderId, formatId));
                List<OrderProductMappingEntity> orderProductMappingEntities = iFormatMappingServices.create(formatMappingEntities);
                if (orderProductMappingEntities != null) {
                    resultSet.setCode(200);
                    resultSet.setData(resultOrder);
                    renderJson(JSON.toJSONString(resultSet));
                } else {
                    Map<String, String[]> paraMap = this.getParaMap();
                    resultSet.setData(paraMap);
                    resultSet.setMessage("创建订单失败");
                    renderJson(JSON.toJSONString(resultSet));
                }
            } else {
                Map<String, String[]> paraMap = this.getParaMap();
                resultSet.setData(paraMap);
                resultSet.setMessage("创建订单失败");
                renderJson(JSON.toJSONString(resultSet));
            }
        } else {
            Map<String, String[]> paraMap = this.getParaMap();
            resultSet.setData(paraMap);
            resultSet.setMessage("创建收货人失败");
            renderJson(JSON.toJSONString(resultSet));
        }
    }


}
