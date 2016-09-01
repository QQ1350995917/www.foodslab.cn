package cn.foodslab.order;

import cn.foodslab.common.response.IResultSet;
import cn.foodslab.common.response.ResultSet;
import cn.foodslab.receiver.IReceiverService;
import cn.foodslab.receiver.ReceiverEntity;
import cn.foodslab.receiver.ReceiverServices;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:22.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class OrderController extends Controller implements IOrderController {
    private IOrderServices iOrderServices = new OrderServices();
    private IReceiverService iReceiverService = new ReceiverServices();
    private IFormatMappingServices iFormatMappingServices = new FormatMappingServices();

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
        IResultSet receiverResultSet = iReceiverService.create(receiverEntity);
        if (receiverResultSet.getCode() == 200) {
            String orderId = UUID.randomUUID().toString();
            OrderEntity orderEntity = new OrderEntity(orderId, accountId, senderName, senderPhone, receiverEntity.getReceiverId(), cost, postage, 1);
            IResultSet orderResultSet = iOrderServices.create(orderEntity);
            if (orderResultSet.getCode() == 200) {
                LinkedList<FormatMappingEntity> formatMappingEntities = new LinkedList<>();
                formatMappingEntities.add(new FormatMappingEntity(UUID.randomUUID().toString(), orderId, formatId));
                IResultSet mappingResultSet = iFormatMappingServices.create(formatMappingEntities);
                if (mappingResultSet.getCode() == 200) {
                    renderJson(JSON.toJSONString(orderResultSet));
                } else {
                    Map<String, String[]> paraMap = this.getParaMap();
                    orderResultSet.setData(paraMap);
                    orderResultSet.setMessage("创建订单失败");
                    renderJson(JSON.toJSONString(orderResultSet));
                }
            } else {
                Map<String, String[]> paraMap = this.getParaMap();
                orderResultSet.setData(paraMap);
                orderResultSet.setMessage("创建订单失败");
                renderJson(JSON.toJSONString(orderResultSet));
            }
        } else {
            Map<String, String[]> paraMap = this.getParaMap();
            receiverResultSet.setData(paraMap);
            receiverResultSet.setMessage("创建收货人失败");
            renderJson(JSON.toJSONString(receiverResultSet));
        }
    }

    @Override
    public void retrieve() {
        String orderId = getPara("orderId");
        if (orderId == null || orderId.trim().equals("")) {
            IResultSet resultSet = new ResultSet();
            resultSet.setCode(404);
            renderJson(JSON.toJSONString(resultSet));
        } else {
            IResultSet orderResultSet = iOrderServices.retrieve(orderId);
            Object data = orderResultSet.getData();
            if (data != null) {
                Map<String, Object> orderEntity = (Map<String, Object>) data;
                IResultSet receiverResultSet = iReceiverService.retrieve(orderEntity.get("receiverId").toString());
                Map<String, Object> receiverEntity = (Map<String, Object>)receiverResultSet.getData();
                orderEntity.putAll(receiverEntity);
                orderResultSet.setData(orderEntity);
            }
            renderJson(JSON.toJSONString(orderResultSet));
        }
    }
}
