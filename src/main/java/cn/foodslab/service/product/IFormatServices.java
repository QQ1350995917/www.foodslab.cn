package cn.foodslab.service.product;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-09-22 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public interface IFormatServices {
    /**
     * 在类型下创建一个新的规格
     * @param typeId 类型的ID
     * @param formatEntity 规格数据
     * @return success 类型数据 fail null
     */
    FormatEntity create(String typeId,FormatEntity formatEntity);

    /**
     * 更新规格
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity update(FormatEntity formatEntity);

    /**
     * 更新规格的状态
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity updateStatus(FormatEntity formatEntity);

    /**
     * 读取类型下的规格
     * @param typeId 类型ID
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> retrieveInType(String typeId);

    /**
     * 通过规格的ID精确读取规格信息
     * @param formatId 规格ID
     * @return success 规格数据 fail null
     */
    FormatEntity retrieveById(String formatId);

    /**
     * 在类型下读取指定类型名称的规格
     * @param typeId 类型ID
     * @param label 规格名称
     * @return success 规格数据 fail null
     */
    FormatEntity retrieveInTypeByLabel(String typeId,String label);
}
