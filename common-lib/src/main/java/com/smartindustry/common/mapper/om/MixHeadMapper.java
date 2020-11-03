package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MixHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.MixHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * MixHeadMapper继承基类
 */
@Mapper
public interface MixHeadMapper extends BaseMapper<MixHeadPO, Long> {
    /**
     * 查询当天的混料工单
     * @return
     */
    List<MixHeadBO> queryMix();

    /**
     * 根据混料表头id和物料id查询当前混料得剩余物料数量
     * @param mixno
     * @param mid
     * @return
     */
    BigDecimal queryByMhid(@Param("mixno") String mixno,@Param("mid") Long mid);

    /**
     * 查看当前混料单所有的期望总数
     * @param mixno
     * @return
     */
    BigDecimal queryTotal(@Param("mixno") String mixno);
}