package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptBodyPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ReceiptBodyMapper继承基类
 */
@Mapper
public interface ReceiptBodyMapper extends BaseMapper<ReceiptBodyPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<ReceiptBO> pageQuery(Map<String, Object> reqData);

    /**
     * 批量插入
     *
     * @param bodyBOs
     */
    void batchInsertBO(List<ReceiptBodyBO> bodyBOs);

    /**
     * 批量删除
     *
     * @param rbIds
     */
    void batchDelete(List<Long> rbIds);

    /**
     * 查询表头ID
     *
     * @param rbIds
     * @return
     */
    List<Long> queryHeadIds(List<Long> rbIds);

    /**
     * 根据表头ID查询数据
     *
     * @param headId
     * @return
     */
    List<ReceiptBodyBO> queryByHeadId(Long headId);

    /**
     * 根据表体ID查询BO
     *
     * @param rbId
     * @return
     */
    ReceiptBodyBO queryByBodyId(Long rbId);

    /**
     * 根據物流IDs 查詢
     *
     * @param mids
     * @return
     */
    List<ReceiptBodyPO> queryByMids(List<Long> mids);

    /**
     * 统计根据入库单状态和 质检状态 的入库单数量
     * @param status 入库单状态
     * @param qaStatus
     * @return
     */

    Integer countHandleNum(@Param("status") Byte status ,@Param("qaStatus") Byte qaStatus);
}