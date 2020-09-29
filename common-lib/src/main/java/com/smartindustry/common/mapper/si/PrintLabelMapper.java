package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PrintLabelMapper继承基类
 */
@Mapper
public interface PrintLabelMapper extends BaseMapper<PrintLabelPO, Long> {
    /**
     * 根据 receipt body id 查询
     *
     * @param rbId
     * @return
     */
    List<PrintLabelPO> queryByReceiptBodyId(@Param("rbid") Long rbId, @Param("status") Byte status);

    /**
     * 根据 rbid 和 pid 查询
     *
     * @param rbid
     * @param pid
     * @return
     */
    PrintLabelBO queryByRbidAndPid(@Param("rbid") Long rbid, @Param("pid") String pid);

    /**
     * 根据Ids查询
     *
     * @param plids
     * @return
     */
    List<PrintLabelPO> queryByIds(List<Long> plids);

    /**
     * 批量更新
     *
     * @param labelPOs
     */
    void batchUpdate(List<PrintLabelPO> labelPOs);

    /**
     * 根据 pid 和 dr 进行查询
     *
     * @param pid
     * @param dr
     * @return
     */
    PrintLabelPO queryByPidAndDr(@Param("pid") String pid, @Param("dr") byte dr);

    /**
     * 更新打印标签
     *
     * @param lid
     * @param plIds
     */
    void updateLidByIds(@Param("lid") Long lid, @Param("plIds") List<Long> plIds);

    /**
     * 根据关联ID删除数据
     *
     * @param relateLabelId
     */
    void deleteByRelateId(Long relateLabelId);

    /**
     * 根据pid查询相关标签信息
     * @param pid
     * @return
     */
    PrintLabelPO queryByPid(String pid);

    /**
     * 根据收料单查询相关标签详细列表
     *
     * @param rbid
     * @return
     */
    List<PrintLabelBO> queryByRbid(@Param("rbid") Long rbid);

    /**
     * 根据sourceNo 查询其他入库单待入库物料列表
     *
     * @param sourceNo
     * @return
     */

    List<PrintLabelBO> queryByTSono(@Param("sourceNo") String sourceNo, @Param("flag") Boolean flag);
}