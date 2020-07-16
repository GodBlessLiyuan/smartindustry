package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MaterialLossBO;
import com.smartindustry.common.bo.om.ScanPickBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PickHeadMapper继承基类
 */
@Mapper
public interface PickHeadMapper extends BaseMapper<PickHeadPO, Long> {
    /**
     * 查询拣货单表头的某些数据项
     *
     * @param reqMap
     * @return
     */
    List<PickHeadPO> pageQueryPickHeadMsg(Map<String, Object> reqMap);

    /**
     * 根据工单拣货单查询所有的打印标签信息
     *
     * @param pickNo
     * @return
     */
    List<ScanPickBO> scanLabelByPickNo(@Param("pickNo") String pickNo);

    /**
     * 扫码拣货的欠料的列表
     *
     * @param materialNo   物料编码
     * @param materialName 物料名称
     * @param flag         用来标识是否点击了只看欠料按钮
     * @return
     */
    List<MaterialLossBO> materialLoss(@Param("pickNo") String pickNo, @Param("materialNo") String materialNo, @Param("materialName") String materialName, @Param("flag") Byte flag);
}