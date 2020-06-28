package com.smartindustry.common.mapper;

import com.smartindustry.common.bo.ReceiptBO;
import com.smartindustry.common.pojo.ReceiptBodyPO;
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
     * @param bodyPOs
     */
    void batchInsert(List<ReceiptBodyPO> bodyPOs);

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
    List<ReceiptBodyPO> queryByHeadId(Long headId);
}