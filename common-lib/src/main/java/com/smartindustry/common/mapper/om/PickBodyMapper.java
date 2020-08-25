package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PickBodyMapper继承基类
 */
@Mapper
public interface PickBodyMapper extends BaseMapper<PickBodyPO, Long> {

    /**
     * 根据表头ID查询
     *
     * @param phId
     * @return
     */
    List<PickBodyBO> queryByHeadId(Long phId);

    /**
     * 根据工单拣货单id和物料编号查询异常说明
     *
     * @param pickHeadId
     * @param materialId
     * @return
     */
    String queryException(@Param("pickHeadId") Long pickHeadId, @Param("materialId") Long materialId);

    /**
     * 更新异常信息
     *
     * @param pickHeadId
     * @param materialId
     * @param exception
     * @return
     */
    int updateException(@Param("pickHeadId") Long pickHeadId,
                        @Param("materialId") Long materialId,
                        @Param("exception") String exception);

    /**
     * 根據物流IDs 查詢
     *
     * @param mids
     * @return
     */
    List<PickBodyPO> queryByMids(List<Long> mids);
}