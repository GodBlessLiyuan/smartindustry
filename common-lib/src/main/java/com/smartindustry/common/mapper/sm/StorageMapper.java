package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.sm.StorageBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StoragePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * StorageMapper继承基类
 */
@Mapper
public interface StorageMapper extends BaseMapper<StoragePO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<StorageBO> pageQuery(Map<String, Object> reqData);

    /**
     * 其他入库单的分页查询
     * @param reqData
     * @return
     */
    List<StorageBO> pageQueryOther(Map<String, Object> reqData);

    /**
     * 查询收料单
     *
     * @param sid
     * @return
     */
    StorageBO queryReceiptBySid(Long sid);

    /**
     * 查询入库单的所有入库物料详情
     * @param storageId
     * @return
     */
    List<PickBodyBO> queryInfo(Long storageId);

    /**
     * 查看所有入库前拣货单相关标签
     * @param storageId
     * @return
     */
    List<String> queryRelatePid(Long storageId);
}