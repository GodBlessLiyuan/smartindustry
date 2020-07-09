package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.sm.LabelRecordBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.LabelRecordPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * LabelRecordMapper继承基类
 */
@Mapper
public interface LabelRecordMapper extends BaseMapper<LabelRecordPO, Long> {
    /**
     * 根据
     * @param rbId
     * @return
     */
    List<LabelRecordBO> queryByReceiptBodyId(Long rbId);
}