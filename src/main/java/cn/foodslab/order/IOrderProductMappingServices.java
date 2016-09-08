package cn.foodslab.order;

import java.util.List;

/**
 * Created by Pengwei Ding on 2016-08-31 17:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IOrderProductMappingServices {
    List<OrderProductMappingEntity> create(List<OrderProductMappingEntity> formatMappingEntities);
}
