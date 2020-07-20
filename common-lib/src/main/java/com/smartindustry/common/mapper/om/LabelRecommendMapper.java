package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.LabelRecommendBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.LabelRecommendPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * LabelRecommendMapper继承基类
 */
@Mapper
public interface LabelRecommendMapper extends BaseMapper<LabelRecommendPO, Long> {
    /**
     * 根据 pbid 查询
     *
     * @param pickBodyId
     * @return
     */
    List<LabelRecommendBO> queryByPbid(Long pickBodyId);
}