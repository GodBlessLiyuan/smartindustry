package com.smartindustry.common.mapper;

import com.smartindustry.common.bo.PrintLabelBO;
import com.smartindustry.common.pojo.PrintLabelPO;
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
    List<PrintLabelPO> queryByReceiptBodyId(Long rbId);

    /**
     * 批量插入
     *
     * @param pos
     */
    void batchInsert(List<PrintLabelPO> pos);

    /**
     * 根据 rbid 和 pid 查询
     *
     * @param rbid
     * @param pid
     * @return
     */
    PrintLabelBO queryByRbidAndPid(@Param("rbid") Long rbid, @Param("pid") String pid);
}