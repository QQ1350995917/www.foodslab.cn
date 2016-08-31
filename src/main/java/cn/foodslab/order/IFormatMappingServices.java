package cn.foodslab.order;

import cn.foodslab.common.response.IResultSet;

import java.util.List;

/**
 * Created by Pengwei Ding on 2016-08-31 17:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFormatMappingServices {
    IResultSet create(List<FormatMappingEntity> formatMappingEntities);
}
