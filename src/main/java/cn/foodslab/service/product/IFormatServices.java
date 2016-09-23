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
     * @param formatEntity 规格数据
     * @return success 类型数据 fail null
     */
    FormatEntity mCreate(FormatEntity formatEntity);

    /**
     * 更新规格
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mUpdate(FormatEntity formatEntity);

    /**
     * 更新规格的状态
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mUpdateStatus(FormatEntity formatEntity);

    /**
     * 后端接口
     * 读取类型下的规格
     * @param typeId 类型ID
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> mRetrieveInType(String typeId);

    /**
     * 前端接口
     * 读取类型下的规格
     * @param typeId 类型ID
     * @return success 规格数据集 fail null
     */
    LinkedList<FormatEntity> retrieveInType(String typeId);

    /**
     * 后端接口
     * 通过规格的ID精确读取规格信息
     * @param formatId 规格ID
     * @return success 规格数据 fail null
     */
    FormatEntity mRetrieveById(String formatId);

    /**
     * 前端接口
     * 通过规格的ID精确读取规格信息
     * @param formatId 规格ID
     * @return success 规格数据 fail null
     */
    FormatEntity retrieveById(String formatId);

    /**
     * 在类型下读取指定类型名称的规格
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mRetrieveInTypeByLabel(FormatEntity formatEntity);

    /**
     * 使一个产品规格数据作为最高推荐
     * @param formatEntity 规格数据
     * @return success 规格数据 fail null
     */
    FormatEntity mKingWeight(FormatEntity formatEntity);

    /**
     * 在产品推荐中交换位置
     * @param formatEntity1 规格数据1
     * @param formatEntity2 规格数据2
     * @return
     */
    FormatEntity[] mSwapWeight(FormatEntity formatEntity1, FormatEntity formatEntity2);
}
