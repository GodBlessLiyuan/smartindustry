package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.ReceiptBodyPO;
import org.apache.ibatis.annotations.Mapper;

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
    void batchInsert(List<ReceiptBodyBO> bodyBOs);

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
}