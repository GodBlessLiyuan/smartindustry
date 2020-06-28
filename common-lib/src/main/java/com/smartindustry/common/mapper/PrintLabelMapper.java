package com.smartindustry.common.mapper;

import com.smartindustry.common.pojo.PrintLabelPO;
import org.apache.ibatis.annotations.Mapper;

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
}