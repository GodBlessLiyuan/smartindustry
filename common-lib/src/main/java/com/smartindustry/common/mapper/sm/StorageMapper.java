package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.StorageBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StoragePO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<Long> pageQuery(Map<String, Object> reqData);

    /**
     * 其他入库单的分页查询
     *
     * @param reqData
     * @return
     */
    List<StorageBO> pageQueryOther(Map<String, Object> reqData);

    /**
     * 根据入库id查询入库相关信息
     *
     * @param sid
     * @return
     */
    StorageBO queryBySid(Long sid);

    /**
     * 查询收料单
     *
     * @param sid
     * @return
     */
    StorageBO queryReceiptBySid(Long sid);

    /**
     * 查询入库单的所有入库物料详情
     *
     * @param storageId
     * @return
     */
    List<PickBodyBO> queryInfo(Long storageId);

    /**
     * 根据入库id查询所有待入库pid
     *
     * @param reqData
     * @return
     */
    List<PrintLabelBO> queryPidInfo(Map<String, Object> reqData);

    /**
     * 查看所有入库前拣货单相关标签
     *
     * @param storageId
     * @return
     */
    List<String> queryRelatePid(Long storageId);

    /**
     * 根据入库id和PID查询标签的详细信息
     *
     * @param storageId
     * @param packageId
     * @return
     */
    PrintLabelBO queryPrint(@Param("sid") Long storageId, @Param("pid") String packageId);

    /**
     * 根据 sids 查询
     *
     * @param sids
     * @return
     */
    List<StorageBO> queryBySids(List<Long> sids);

}