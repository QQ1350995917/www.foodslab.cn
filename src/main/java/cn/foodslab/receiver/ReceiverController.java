package cn.foodslab.receiver;

import cn.foodslab.common.response.IResultSet;
import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by Pengwei Ding on 2016-08-31 14:42.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class ReceiverController extends Controller implements IReceiverController {

    private IReceiverService iReceiverService = new ReceiverServices();

    @Override
    public void create() {
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
        String accountId = getPara("accountId");
        ReceiverEntity receiverEntity = new ReceiverEntity(receiverId, province, city, county, town, village, append, name, phone0, phone1, 1, accountId);
        IResultSet resultSet = iReceiverService.create(receiverEntity);
        renderJson(JSON.toJSONString(resultSet));
    }
}
