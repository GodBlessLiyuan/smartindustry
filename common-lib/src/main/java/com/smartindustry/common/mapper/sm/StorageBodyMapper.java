package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * StorageBodyMapper继承基类
 */
@Mapper
public interface StorageBodyMapper extends BaseMapper<StorageBodyPO, Long> {
    /**
     * 采购单表体的批量更新
     * @param pos
     */
    void batchUpdate(List<StorageBodyPO> pos);

    /**
     * 批量删除
     * @param sbids
     */
    void deleteBatch(List<Long> sbids);

    /**
     * 查询采购订单表头得表体id
     * @param shid
     * @return
     */
    List<Long> querySbids(Long shid);

    /**
     * 根据入库单id和物料id
     * @param shid
     * @param lid
     * @return
     */
    StorageBodyPO queryByShidAndLid(Long shid,Long lid);

    /**
     * 查看当前入库单下所有的物料类型
     * @param shid
     * @return
     */
    List<MaterialPO> queryMaterial(Long shid);
}